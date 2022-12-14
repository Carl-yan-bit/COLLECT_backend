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
            return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
        }
        if (userService.getUserByUid(projectVO.getUserId()).getUserRole().equals(CONST.USER_ROLE_WORKER)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
        }
        if(StringUtils.hasText(projectVO.getName())&&StringUtils.hasText(projectVO.getDescription())&&StringUtils.hasText(projectVO.getTestTime().toString())){
            Project project = new Project(projectVO);
            project.setCreateTime(new Date());
            if(projectMapper.insert(project)==1)
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",toProjectVO(project));
            return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
        }
        else {
            return new ResultVO<>(CONST.REQUEST_FAIL,"?????????????????????!");
        }
    }

    @Override
    public ResultVO<ProjectVO> updateProject(ProjectVO projectVO) {
        if(userService.getUserByUid(projectVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"?????????????????????!");
        }
        if (userService.getUserByUid(projectVO.getUserId()).getUserRole().equals(CONST.USER_ROLE_WORKER)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
        }
        if(StringUtils.hasText(projectVO.getName())&&StringUtils.hasText(projectVO.getDescription())&&StringUtils.hasText(projectVO.getState())&&StringUtils.hasText(projectVO.getTestTime().toString())){
            Project project = new Project(projectVO);
            project.setCreateTime(new Date());
            if(projectVO.getTestTime().after(new Date())){
                project.setState(CONST.STATE_OPEN);
            }
            if(projectMapper.selectByPrimaryKey(project.getId())!=null){
                //????????????????????????????????????????????????????????????
                if(project.getClickTimes()==null){
                    project.setClickTimes(projectMapper.selectByPrimaryKey(project.getId()).getClickTimes());
                }

                if(projectMapper.updateByPrimaryKey(project)==1)
                    return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",toProjectVO(project));
                return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
            }
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????????????????????????????");
        }
        else {
            return new ResultVO<>(CONST.REQUEST_FAIL,"?????????????????????!");
        }
    }

    @Override
    public ResultVO<ProjectVO> deleteProject(Integer pid) {
        if(projectMapper.selectByPrimaryKey(pid)!=null){
            Project project = projectMapper.selectByPrimaryKey(pid);
            projectMapper.deleteByPrimaryKey(pid);
            for(UserProject userProject:userProjectMapper.selectByProjects(pid)){
                //??????????????????????????????
                userProjectMapper.deleteByPrimaryKey(userProject.getId());
            }
            //???????????????????????????
            if(!taskService.deleteAllTasksByProject(pid)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"?????????????????????");
            }
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",new ProjectVO(project));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????????????????????????????");
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
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",userProjectMapper.selectByProjects(pid).size());
    }

    @Override
    public ResultVO<ProjectVO> quitProject(UserProjectVO userProjectVO) {
        Integer uid = userProjectVO.getUserId();
        Integer projectId = userProjectVO.getProjectId();
        if(projectMapper.selectByPrimaryKey(projectId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(getProjectById(projectId).getState().equals(CONST.STATE_CLOSED)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????");
        }
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                if(userProjectMapper.deleteByPrimaryKey(userProject.getId())==1)
                    return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",toProjectVO(projectMapper.selectByPrimaryKey(projectId)));
                return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
            }
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"????????????????????????(????????????)");
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
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(projectMapper.selectByPrimaryKey(projectId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????");
        }
        if(getProjectById(projectId).getState().equals(CONST.STATE_CLOSED)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????");
        }
        if(userProjectMapper.selectByProjects(projectId).size()>=projectMapper.selectByPrimaryKey(projectId).getWorkerAmount()){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????!");
        }
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
            }
        }
        UserProject up = new UserProject(userProjectVO);
        if(userProjectMapper.insert(up)==1)
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",toProjectVO(projectMapper.selectByPrimaryKey(projectId)));
        return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
    }

    @Override
    public ResultVO<ProjectVO> getProjectByIdWithUid(Integer projectId, Integer uid) {
        ProjectVO project = getProjectById(projectId);
        onClick(projectId);
        if(project==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
        }
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                project.setJoined(true);
                break;
            }
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",project);
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
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????");
        }
        project.setClickTimes(project.getClickTimes()+1);
        updateProject(new ProjectVO(project));
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????");
    }

    @Override
    public ResultVO<List<ProjectVO>> getRecommendation(Integer uid) {
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",setMemberNum(recommendStrategyFactory.getRecommendStrategy(uid).getRecommend(uid,recommendStrategyMapper.selectOnUse())));
    }

    @Override
    public boolean isActive(Project p) {
        return userProjectMapper.selectByProjects(p.getId()).size()<p.getWorkerAmount()&&p.getState().equals(CONST.STATE_OPEN);
    }
}
