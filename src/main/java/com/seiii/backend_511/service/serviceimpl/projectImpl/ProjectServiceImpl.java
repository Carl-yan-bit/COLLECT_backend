package com.seiii.backend_511.service.serviceimpl.projectImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ProjectMapper;
import com.seiii.backend_511.mapperservice.RecommendStrategyMapper;
import com.seiii.backend_511.mapperservice.TypeMapper;
import com.seiii.backend_511.mapperservice.UserProjectMapper;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.po.task.Task;
import com.seiii.backend_511.service.device.DeviceService;
import com.seiii.backend_511.service.file.FileService;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.recommend.RecommendStrategyFactory;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.PageInfoUtil;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.project.UserProjectVO;
import com.seiii.backend_511.vo.task.TaskVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private TaskService taskService;
    @Resource
    private UserService userService;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private UserProjectMapper userProjectMapper;
    @Resource
    private DeviceService deviceService;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private RecommendStrategyFactory recommendStrategyFactory;
    @Resource
    private RecommendStrategyMapper recommendStrategyMapper;


    @Override
    public ResultVO<ProjectVO> createProject(ProjectVO projectVO) {
        projectVO.setId(null);
        projectVO.setClickTimes(0);
        if(userService.getUserByUid(projectVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有用户");
        }
        if (userService.getUserByUid(projectVO.getUserId()).getUserRole().equals(CONST.USER_ROLE_WORKER)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        if(StringUtils.hasText(projectVO.getName())&&StringUtils.hasText(projectVO.getDescription())&&StringUtils.hasText(projectVO.getTestTime().toString())){
            Project project = new Project(projectVO);
            project.setCreateTime(new Date());
            if(projectMapper.insert(project)==1)
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"创建成功",toProjectVO(project));
            return new ResultVO<>(CONST.REQUEST_FAIL,"创建失败");
        }
        else {
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目定义不完全!");
        }
    }

    @Override
    public ResultVO<ProjectVO> updateProject(ProjectVO projectVO) {
        if(userService.getUserByUid(projectVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目定义不完全!");
        }
        if (userService.getUserByUid(projectVO.getUserId()).getUserRole().equals(CONST.USER_ROLE_WORKER)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        if(StringUtils.hasText(projectVO.getName())&&StringUtils.hasText(projectVO.getDescription())&&StringUtils.hasText(projectVO.getState())&&StringUtils.hasText(projectVO.getTestTime().toString())){
            Project project = new Project(projectVO);
            project.setCreateTime(new Date());
            if(projectVO.getTestTime().after(new Date())){
                project.setState(CONST.STATE_OPEN);
            }
            if(projectMapper.selectByPrimaryKey(project.getId())!=null){
                //如果没有设置点击次数，就将它设置为原来的
                if(project.getClickTimes()==null){
                    project.setClickTimes(projectMapper.selectByPrimaryKey(project.getId()).getClickTimes());
                }

                if(projectMapper.updateByPrimaryKey(project)==1)
                    return new ResultVO<>(CONST.REQUEST_SUCCESS,"更新成功",toProjectVO(project));
                return new ResultVO<>(CONST.REQUEST_FAIL,"创建失败");
            }
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个项目，不要试图更改了");
        }
        else {
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目定义不完全!");
        }
    }

    @Override
    public ResultVO<ProjectVO> deleteProject(Integer pid) {
        if(projectMapper.selectByPrimaryKey(pid)!=null){
            Project project = projectMapper.selectByPrimaryKey(pid);
            projectMapper.deleteByPrimaryKey(pid);
            for(UserProject userProject:userProjectMapper.selectByProjects(pid)){
                //删除项目下所有的用户
                userProjectMapper.deleteByPrimaryKey(userProject.getId());
            }
            //删除项目下所有任务
            if(!taskService.deleteAllTasksByProject(pid)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"子任务删除失败");
            }
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"删除成功",new ProjectVO(project));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个项目，不要试图删除了");
    }

    @Override
    public PageInfo<ProjectVO> getProjectsByUserId(Integer uid,Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        List<ProjectVO> ans = new ArrayList<>();
        for(Project p:projectMapper.selectByUserId(uid)){
            ans.add(setMemberNum(p));
        }

        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public PageInfo<ProjectVO> getJoinedProjects(Integer uid, Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        List<ProjectVO> projectVOS = new ArrayList<>();
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            projectVOS.add(setMemberNum(projectMapper.selectByPrimaryKey(userProject.getProjectId())));
        }
        return PageInfoUtil.ListToPageInfo(projectVOS,currPage);
    }
    @Override
    public List<ProjectVO> getAllJoinedProjects(Integer uid){
        List<ProjectVO> projectVOS = new ArrayList<>();
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            projectVOS.add(setMemberNum(projectMapper.selectByPrimaryKey(userProject.getProjectId())));
        }
        return projectVOS;
    }
    @Override
    public List<ProjectVO> getAllActiveProjects(){
        List<Project> all= projectMapper.selectAll();
        List<ProjectVO> ans = new ArrayList<>();
        for(Project p:all){
            if(userProjectMapper.selectByProjects(p.getId()).size()<p.getWorkerAmount()&&p.getState().equals(CONST.STATE_OPEN)){
                ans.add(setMemberNum(p));
            }
        }
        return ans;
    }
    @Override
    public PageInfo<ProjectVO> getActiveProjects(Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        List<Project> all= projectMapper.selectAll();
        List<ProjectVO> ans = new ArrayList<>();
        for(Project p:all){
            if(userProjectMapper.selectByProjects(p.getId()).size()<p.getWorkerAmount()&&p.getState().equals(CONST.STATE_OPEN)){
                ans.add(setMemberNum(p));
            }
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public ResultVO<Integer> getProjectNumbers(Integer pid) {
        if(projectMapper.selectByPrimaryKey(pid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个项目");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",userProjectMapper.selectByProjects(pid).size());
    }

    @Override
    public ResultVO<ProjectVO> quitProject(UserProjectVO userProjectVO) {
        Integer uid = userProjectVO.getUserId();
        Integer projectId = userProjectVO.getProjectId();
        if(projectMapper.selectByPrimaryKey(projectId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个项目");
        }
        if(getProjectById(projectId).getState().equals(CONST.STATE_CLOSED)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"任务已关闭");
        }
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                if(userProjectMapper.deleteByPrimaryKey(userProject.getId())==1)
                    return new ResultVO<>(CONST.REQUEST_SUCCESS,"退出成功",toProjectVO(projectMapper.selectByPrimaryKey(projectId)));
                return new ResultVO<>(CONST.REQUEST_FAIL,"退出失败");
            }
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"您就不在任务里吧(流汗黄豆)");
    }
    public List<ProjectVO> setMemberNum(List<Project> projectList){
        List<ProjectVO> ans = new ArrayList<>();
        for(Project project:projectList){
            ans.add(setMemberNum(project));
        }
        return ans;
    }
    private ProjectVO setMemberNum(Project po){
        if(getProjectNumbers(po.getId()).getCode().equals(CONST.REQUEST_FAIL)){
            return toProjectVO(po);
        }
        ProjectVO vo = toProjectVO(po);
        vo.setMemberNum(getProjectNumbers(po.getId()).getData());
        return vo;
    }
    @Override
    public ResultVO<ProjectVO> joinProject(UserProjectVO userProjectVO) {
        Integer uid = userProjectVO.getUserId();
        Integer projectId = userProjectVO.getProjectId();
        userProjectVO.setJoinTime(new Date());
        if(userService.getUserByUid(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(projectMapper.selectByPrimaryKey(projectId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目不存在");
        }
        if(getProjectById(projectId).getState().equals(CONST.STATE_CLOSED)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"任务已关闭");
        }
        if(userProjectMapper.selectByProjects(projectId).size()>=projectMapper.selectByPrimaryKey(projectId).getWorkerAmount()){
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目人数已满!");
        }
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"已经在项目中");
            }
        }
        UserProject up = new UserProject(userProjectVO);
        if(userProjectMapper.insert(up)==1)
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"加入成功",toProjectVO(projectMapper.selectByPrimaryKey(projectId)));
        return new ResultVO<>(CONST.REQUEST_FAIL,"加入失败");
    }

    @Override
    public ResultVO<ProjectVO> getProjectByIdWithUid(Integer projectId, Integer uid) {
        ProjectVO project = getProjectById(projectId);
        onClick(projectId);
        if(project==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"查询失败");
        }
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                project.setJoined(true);
                break;
            }
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",project);
    }

    @Override
    public PageInfo<ProjectVO> getAllProjects(Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        List<ProjectVO> projectVO = new ArrayList<>();
        for(Project project:projectMapper.selectAll()){
            ProjectVO p = toProjectVO(project);
            p.setMemberNum(userProjectMapper.selectByProjects(p.getId()).size());
            projectVO.add(p);
        }
        return PageInfoUtil.ListToPageInfo(projectVO,currPage);
    }

    @Override
    public ProjectVO getProjectById(Integer projectId) {
        if(projectMapper.selectByPrimaryKey(projectId)==null){
            return null;
        }
        return setMemberNum(projectMapper.selectByPrimaryKey(projectId));
    }
    private ProjectVO toProjectVO(Project project){
        ProjectVO projectVO = new ProjectVO(project);
        if(deviceService.getDeviceById(projectVO.getDeviceId())!=null)
            projectVO.setDeviceInfo(deviceService.getDeviceById(projectVO.getDeviceId()).getDeviceInfo());
        if(typeMapper.selectByPrimaryKey(projectVO.getType())!=null)
            projectVO.setTypeInfo(typeMapper.selectByPrimaryKey(projectVO.getType()).getTypeInfo());
        return projectVO;
    }
    private List<ProjectVO> toProjectVO(List<Project> projectList){
        List<ProjectVO> projectVOList = new ArrayList<>();
        for(Project project:projectList){
            projectVOList.add(toProjectVO(project));
        }
        return projectVOList;
    }
    public List<Project> selectAllByClickOrder(int nums,Integer uid){
        return projectMapper.selectAllByClickOrder(nums,uid);
    }
    @Override
    public ResultVO<ProjectVO> onClick(Integer pid) {
        Project project = projectMapper.selectByPrimaryKey(pid);
        if(project==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"失败");
        }
        project.setClickTimes(project.getClickTimes()+1);
        updateProject(new ProjectVO(project));
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功");
    }

    @Override
    public ResultVO<List<ProjectVO>> getRecommendation(Integer uid) {
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",setMemberNum(recommendStrategyFactory.getRecommendStrategy(uid).getRecommend(uid,recommendStrategyMapper.selectOnUse())));
    }

    @Override
    public boolean isActive(Project p) {
        return userProjectMapper.selectByProjects(p.getId()).size()<p.getWorkerAmount()&&p.getState().equals(CONST.STATE_OPEN);
    }
}
