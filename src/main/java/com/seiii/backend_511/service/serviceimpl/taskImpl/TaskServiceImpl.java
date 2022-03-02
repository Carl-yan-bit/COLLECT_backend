package com.seiii.backend_511.service.serviceimpl.taskImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ProjectMapper;
import com.seiii.backend_511.mapperservice.TaskMapper;
import com.seiii.backend_511.mapperservice.UserTaskMapper;
import com.seiii.backend_511.po.task.Task;
import com.seiii.backend_511.po.task.UserTask;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.task.UserTaskVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    TaskMapper taskMapper;
    @Resource
    ProjectService projectService;
    @Resource
    UserService userService;
    @Resource
    UserTaskMapper userTaskMapper;
    @Resource
    ReportService reportService;
    @Override
    public ResultVO<TaskVO> createTask(TaskVO taskVO) {
        Task task = new Task(taskVO);
        if(StringUtils.hasText(task.getName())&&StringUtils.hasText(task.getDescription())&&StringUtils.hasText(task.getState())&&StringUtils.hasText(task.getTestTime().toString())&&StringUtils.hasText(task.getWorkerAmount().toString())){
            if(projectService.getProjectById(taskVO.getProjectId())==null){
                return new ResultVO<>(CONST.REQUEST_FAIL,"不存在该项目");
            }
            if(task.getWorkerAmount()>projectService.getProjectById(taskVO.getProjectId()).getWorkerAmount()){
                return new ResultVO<>(CONST.REQUEST_FAIL,"任务人数不应该超过项目最大人数");
            }
            task.setCreateTime(new Date());
            taskMapper.insert(task);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"任务创建成功",new TaskVO(task));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"任务信息填写不全");
    }

    @Override
    public ResultVO<TaskVO> updateTask(TaskVO taskVO) {
        Task task = new Task(taskVO);
        if(StringUtils.hasText(task.getName())&&StringUtils.hasText(task.getDescription())&&StringUtils.hasText(task.getState())&&StringUtils.hasText(task.getTestTime().toString())&&StringUtils.hasText(task.getWorkerAmount().toString())){
            if(projectService.getProjectById(taskVO.getProjectId())==null){
                return new ResultVO<>(CONST.REQUEST_FAIL,"任务所属项目不存在");
            }
            if(task.getWorkerAmount()>projectService.getProjectById(taskVO.getProjectId()).getWorkerAmount()){
                return new ResultVO<>(CONST.REQUEST_FAIL,"任务人数不应该超过项目最大人数");
            }
            Task oldTask = taskMapper.selectByPrimaryKey(task.getId());
            if(oldTask==null){
                return new ResultVO<>(CONST.REQUEST_FAIL,"任务不存在");
            }
            task.setCreateTime(oldTask.getCreateTime());
            taskMapper.updateByPrimaryKey(task);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"任务更新成功",new TaskVO(task));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"任务信息填写不全");
    }

    @Override
    public ResultVO<TaskVO> deleteTask(Integer taskID) {
        if(taskMapper.selectByPrimaryKey(taskID)!=null){
            TaskVO taskVO = new TaskVO(taskMapper.selectByPrimaryKey(taskID));
            taskMapper.deleteByPrimaryKey(taskID);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"删除成功",taskVO);
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个项目");
    }

    @Override
    public PageInfo<TaskVO> getTaskByProject(Integer project_id,Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;
        PageHelper.startPage(currPage,CONST.PAGE_SIZE);
        return new PageInfo<>(getALlTasksByProject(project_id));
    }

    @Override
    public PageInfo<TaskVO> getActiveTaskByProject(Integer project_id, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;

        List<TaskVO> ans = new ArrayList<>();
        for(Task task:taskMapper.selectByProject(project_id)){
            if(userTaskMapper.selectByTask(task.getId()).size()<task.getWorkerAmount()&&task.getState().equals(CONST.STATE_OPEN)){
                ans.add(new TaskVO(task));
            }
        }
        PageHelper.startPage(currPage,CONST.PAGE_SIZE);
        return new PageInfo<>(ans);
    }

    @Override
    public ResultVO<Integer> getMemberNum(Integer taskId) {
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"不存在这个需求");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",userTaskMapper.selectByTask(taskId).size());
    }

    @Override
    public PageInfo<TaskVO> getHistoryTasks(Integer uid, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;

        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            if(task.getState().equals(CONST.STATE_CLOSED)){
                ans.add(new TaskVO(task));
            }
        }
        PageHelper.startPage(currPage,CONST.PAGE_SIZE);
        return new PageInfo<>(ans);
    }

    @Override
    public PageInfo<TaskVO> getTodoTasks(Integer uid, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;

        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            if(task.getState().equals(CONST.STATE_OPEN)&&reportService.getReportByTaskAndUID(taskID.getTaskId(),uid).getCode().equals(CONST.REQUEST_FAIL)){
                //任务开放，且用户没有提交报告
                ans.add(new TaskVO(task));
            }
        }
        PageHelper.startPage(currPage,CONST.PAGE_SIZE);
        return new PageInfo<>(ans);
    }

    @Override
    public TaskVO getTaskByID(Integer ID) {
        return taskMapper.selectByPrimaryKey(ID)==null?null:new TaskVO(taskMapper.selectByPrimaryKey(ID));
    }

    @Override
    public PageInfo<TaskVO> getNowTasks(Integer uid, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;

        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            if(task.getState().equals(CONST.STATE_OPEN)){
                ans.add(new TaskVO(task));
            }
        }
        PageHelper.startPage(currPage,CONST.PAGE_SIZE);
        return new PageInfo<>(ans);
    }

    @Override
    public List<TaskVO> getTasksByUser(Integer uid) {
        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            ans.add(new TaskVO(task));
        }
        return ans;
    }

    @Override
    public ResultVO<TaskVO> joinTask(UserTaskVO userTaskVO) {
        Integer uid = userTaskVO.getUserId();
        Integer taskId = userTaskVO.getTaskId();
        if(userService.getUserByUid(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        for (UserTask userTask:userTaskMapper.selectByUid(uid)){
            if(userTask.getTaskId().equals(taskId)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"已经在任务中");
            }
        }
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个任务");
        }
        if(userTaskMapper.selectByTask(taskId).size()>=taskMapper.selectByPrimaryKey(taskId).getWorkerAmount()){
            return new ResultVO<>(CONST.REQUEST_FAIL,"任务人数已满");
        }
        UserTask userTask = new UserTask(userTaskVO);
        userTaskMapper.insert(userTask);
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"任务加入成功",new TaskVO(taskMapper.selectByPrimaryKey(taskId)));
    }

    @Override
    public ResultVO<TaskVO> quitTask(UserTaskVO userTaskVO) {
        Integer uid = userTaskVO.getUserId();
        Integer taskId = userTaskVO.getTaskId();
        if(userService.getUserByUid(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个任务");
        }
        for (UserTask userTask:userTaskMapper.selectByUid(uid)){
            if(userTask.getTaskId().equals(taskId)){
                userTaskMapper.deleteByPrimaryKey(userTask.getId());
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"退出成功",new TaskVO(taskMapper.selectByPrimaryKey(taskId)));
            }
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"不在任务中");
    }
    //下面是一些工具方法

    @Override
    public List<TaskVO> getALlTasksByProject(Integer project_id) {
        List<TaskVO> ans = new ArrayList<>();
        for(Task task:taskMapper.selectByProject(project_id)){
            ans.add(new TaskVO(task));
        }
        return ans;
    }

    @Override
    public boolean deleteAllTasksByProject(Integer project_id) {
        for(Task task:taskMapper.selectByProject(project_id)){
            taskMapper.deleteByPrimaryKey(task.getId());
        }
        return getALlTasksByProject(project_id).size()==0;
    }
}
