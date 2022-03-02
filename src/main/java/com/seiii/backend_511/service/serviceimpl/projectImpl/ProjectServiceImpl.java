package com.seiii.backend_511.service.serviceimpl.projectImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ProjectMapper;
import com.seiii.backend_511.mapperservice.UserProjectMapper;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.po.task.Task;
import com.seiii.backend_511.service.file.FileService;
import com.seiii.backend_511.service.project.ProjectService;
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
    private FileService fileService;
    @Resource
    private TaskService taskService;
    @Resource
    private UserService userService;
    @Resource
    private ReportService reportService;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private UserProjectMapper userProjectMapper;
    @Override
    public ResultVO<ProjectVO> createProject(ProjectVO projectVO) {
        if(userService.getUserByUid(projectVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目定义不完全!");
        }
        if(StringUtils.hasText(projectVO.getName())&&StringUtils.hasText(projectVO.getDescription())&&StringUtils.hasText(projectVO.getType())&&StringUtils.hasText(projectVO.getTestTime().toString())){
            Project project = new Project(projectVO);
            project.setCreateTime(new Date());
            projectMapper.insert(project);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"创建成功",new ProjectVO(project));
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
        if(StringUtils.hasText(projectVO.getName())&&StringUtils.hasText(projectVO.getDescription())&&StringUtils.hasText(projectVO.getType())&&StringUtils.hasText(projectVO.getTestTime().toString())){
            Project project = new Project(projectVO);
            project.setCreateTime(new Date());
            if(projectMapper.selectByPrimaryKey(project.getId())!=null){
                projectMapper.updateByPrimaryKey(project);
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"更新成功",new ProjectVO(project));
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
        PageHelper.startPage(currPage, CONST.PAGE_SIZE);
        PageInfo<Project> po = new PageInfo<>(projectMapper.selectByUserId(uid));
        return PageInfoUtil.convert(po,ProjectVO.class);
    }

    @Override
    public PageInfo<ProjectVO> getJoinedProjects(Integer uid, Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        List<ProjectVO> projectVOS = new ArrayList<>();
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            projectVOS.add(new ProjectVO(projectMapper.selectByPrimaryKey(userProject.getProjectId())));
        }
        return PageInfoUtil.ListToPageInfo(projectVOS,currPage);
    }

    @Override
    public PageInfo<ProjectVO> getActiveProjects(Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        List<Project> all= projectMapper.selectAll();
        List<ProjectVO> ans = new ArrayList<>();
        for(Project p:all){
            if(userProjectMapper.selectByProjects(p.getId()).size()<p.getWorkerAmount()){
                ans.add(new ProjectVO(p));
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
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                userProjectMapper.deleteByPrimaryKey(userProject.getId());
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"退出成功",new ProjectVO(projectMapper.selectByPrimaryKey(projectId)));
            }
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"您就不在任务里吧(流汗黄豆)");
    }

    @Override
    public ResultVO<ProjectVO> joinProject(UserProjectVO userProjectVO) {
        Integer uid = userProjectVO.getUserId();
        Integer projectId = userProjectVO.getProjectId();
        if(userService.getUserByUid(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        for(UserProject userProject:userProjectMapper.selectByUser(uid)){
            if(userProject.getProjectId().equals(projectId)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"已经在项目中");
            }
        }
        if(projectMapper.selectByPrimaryKey(projectId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目不存在");
        }
        if(userProjectMapper.selectByProjects(projectId).size()>=projectMapper.selectByPrimaryKey(projectId).getWorkerAmount()){
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目人数已满!");
        }
        UserProject up = new UserProject(userProjectVO);
        userProjectMapper.insert(up);
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"加入成功",new ProjectVO(projectMapper.selectByPrimaryKey(projectId)));
    }

    @Override
    public PageInfo<ProjectVO> getAllProjects(Integer currPage) {
        if(currPage==null || currPage<1) currPage=1;
        List<ProjectVO> projectVO = new ArrayList<>();
        for(Project project:projectMapper.selectAll()){
            ProjectVO p = new ProjectVO(project);
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
        return new ProjectVO(projectMapper.selectByPrimaryKey(projectId));
    }
}
