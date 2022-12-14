package com.seiii.backend_511.service.serviceimpl.taskImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ProjectMapper;
import com.seiii.backend_511.mapperservice.TaskMapper;
import com.seiii.backend_511.mapperservice.TypeMapper;
import com.seiii.backend_511.mapperservice.UserTaskMapper;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.task.Task;
import com.seiii.backend_511.po.task.UserTask;
import com.seiii.backend_511.service.device.DeviceService;
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
    @Resource
    private DeviceService deviceService;
    @Resource
    private TypeMapper typeMapper;
    @Override
    public ResultVO<TaskVO> createTask(TaskVO taskVO) {
        Task task = new Task(taskVO);
        task.setId(null);
        if(StringUtils.hasText(task.getName())&&StringUtils.hasText(task.getDescription())&&StringUtils.hasText(task.getState())&&StringUtils.hasText(task.getTestTime().toString())&&StringUtils.hasText(task.getWorkerAmount().toString())){
            if(projectService.getProjectById(taskVO.getProjectId())==null){
                return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
            }
            if(task.getWorkerAmount()>projectService.getProjectById(taskVO.getProjectId()).getWorkerAmount()){
                return new ResultVO<>(CONST.REQUEST_FAIL,"?????????????????????????????????????????????");
            }
            task.setCreateTime(new Date());
            if(taskMapper.insert(task)==1)
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????????????????",toTaskVO(task));
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"????????????????????????");
    }

    @Override
    public ResultVO<TaskVO> updateTask(TaskVO taskVO) {
        Task task = new Task(taskVO);
        if(StringUtils.hasText(task.getName())&&StringUtils.hasText(task.getDescription())&&StringUtils.hasText(task.getState())&&StringUtils.hasText(task.getTestTime().toString())&&StringUtils.hasText(task.getWorkerAmount().toString())){
            if(projectService.getProjectById(taskVO.getProjectId())==null){
                return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????????????????");
            }
            if(task.getWorkerAmount()>projectService.getProjectById(taskVO.getProjectId()).getWorkerAmount()){
                return new ResultVO<>(CONST.REQUEST_FAIL,"?????????????????????????????????????????????");
            }
            Task oldTask = taskMapper.selectByPrimaryKey(task.getId());
            if(oldTask==null){
                return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????");
            }
            task.setCreateTime(oldTask.getCreateTime());
            if(task.getTestTime().after(new Date())){
                task.setState(CONST.STATE_OPEN);
            }
            if(taskMapper.updateByPrimaryKey(task)==1)
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????????????????",toTaskVO(task));
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"????????????????????????");
    }

    @Override
    public ResultVO<TaskVO> deleteTask(Integer taskID) {
        if(taskMapper.selectByPrimaryKey(taskID)!=null){
            TaskVO taskVO = toTaskVO(taskMapper.selectByPrimaryKey(taskID));
            if(taskMapper.deleteByPrimaryKey(taskID)==1)
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",taskVO);
            return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
    }

    @Override
    public PageInfo<TaskVO> getTaskByProject(Integer project_id,Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;
        List<TaskVO> ans = new ArrayList<>();
        for(TaskVO task:getALlTasksByProject(project_id)){
            task.setNowMembers(userTaskMapper.selectByTask(task.getId()).size());
            ans.add(task);
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage,50);
    }

    @Override
    public PageInfo<TaskVO> getActiveTaskByProject(Integer project_id, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;
        List<TaskVO> ans = new ArrayList<>();
        for(Task task:taskMapper.selectByProject(project_id)){
            if(userTaskMapper.selectByTask(task.getId()).size()<task.getWorkerAmount()&&task.getState().equals(CONST.STATE_OPEN)){
                ans.add(setMemberNum(task));
            }
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public ResultVO<Integer> getMemberNum(Integer taskId) {
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"?????????????????????");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",userTaskMapper.selectByTask(taskId).size());
    }

    @Override
    public PageInfo<TaskVO> getHistoryTasks(Integer uid, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;

        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            if(task.getState().equals(CONST.STATE_CLOSED)){
                ans.add(setMemberNum(task));
            }
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public PageInfo<TaskVO> getTaskByProjectWithUID(Integer project_id, Integer uid, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;
        List<TaskVO> ans = new ArrayList<>();
        for(TaskVO task:getALlTasksByProject(project_id)){
            for(UserTask taskID:userTaskMapper.selectByUid(uid)){
                if(task.getId().equals(taskID.getTaskId())){
                    task.setJoined(true);
                    if(reportService.getReportByTaskAndUID(task.getId(),uid).getCode().equals(CONST.REQUEST_SUCCESS)){
                        //????????????????????????????????????
                        task.setFinished(true);
                    }
                }
            }
            ans.add(task);
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public PageInfo<TaskVO> getTodoTasks(Integer uid, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;

        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            if(task.getState().equals(CONST.STATE_OPEN)&&reportService.getReportByTaskAndUID(taskID.getTaskId(),uid).getCode().equals(CONST.REQUEST_FAIL)){
                //??????????????????????????????????????????
                ans.add(setMemberNum(task));
            }
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public TaskVO getTaskByID(Integer ID) {
        return taskMapper.selectByPrimaryKey(ID)==null?null:setMemberNum(taskMapper.selectByPrimaryKey(ID));
    }

    @Override
    public PageInfo<TaskVO> getNowTasks(Integer uid, Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;

        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            if(task.getState().equals(CONST.STATE_OPEN)){
                ans.add(setMemberNum(task));
            }
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public List<TaskVO> getTasksByUser(Integer uid) {
        List<TaskVO> ans = new ArrayList<>();
        for(UserTask taskID:userTaskMapper.selectByUid(uid)){
            Task task = taskMapper.selectByPrimaryKey(taskID.getTaskId());
            ans.add(setMemberNum(task));
        }
        return ans;
    }

    @Override
    public ResultVO<TaskVO> joinTask(UserTaskVO userTaskVO) {
        Integer uid = userTaskVO.getUserId();
        Integer taskId = userTaskVO.getTaskId();
        if(userService.getUserByUid(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        for (UserTask userTask:userTaskMapper.selectByUid(uid)){
            if(userTask.getTaskId().equals(taskId)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
            }
        }
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(userTaskMapper.selectByTask(taskId).size()>=taskMapper.selectByPrimaryKey(taskId).getWorkerAmount()){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(taskMapper.selectByPrimaryKey(taskId).getState().equals(CONST.STATE_CLOSED)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????");
        }
        UserTask userTask = new UserTask(userTaskVO);

        UserProjectVO vo = new UserProjectVO();
        vo.setProjectId(taskMapper.selectByPrimaryKey(taskId).getProjectId());
        vo.setUserId(uid);
        ResultVO<ProjectVO> res = projectService.joinProject(vo);
        if((res.getCode().equals(CONST.REQUEST_SUCCESS)||res.getMsg().equals("??????????????????"))&&userTaskMapper.insert(userTask)==1)
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????????????????",toTaskVO(taskMapper.selectByPrimaryKey(taskId)));
        return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
    }

    @Override
    public ResultVO<TaskVO> quitTask(UserTaskVO userTaskVO) {
        Integer uid = userTaskVO.getUserId();
        Integer taskId = userTaskVO.getTaskId();
        if(userService.getUserByUid(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(taskMapper.selectByPrimaryKey(taskId).getState().equals(CONST.STATE_CLOSED)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????");
        }
        for (UserTask userTask:userTaskMapper.selectByUid(uid)){
            if(userTask.getTaskId().equals(taskId)){
                if(userTaskMapper.deleteByPrimaryKey(userTask.getId())==1)
                    return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",toTaskVO(taskMapper.selectByPrimaryKey(taskId)));
                return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
            }
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"???????????????");
    }
    //???????????????????????????

    @Override
    public List<TaskVO> getALlTasksByProject(Integer project_id) {
        List<TaskVO> ans = new ArrayList<>();
        for(Task task:taskMapper.selectByProject(project_id)){
            ans.add(setMemberNum(task));
        }
        return ans;
    }

    @Override
    public ResultVO<TaskVO> getTaskByIdAndUid(Integer ID, Integer uid) {
        TaskVO taskVO = getTaskByID(ID);
        if(taskVO==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????");
        }
        for (UserTask userTask:userTaskMapper.selectByUid(uid)){
            if(userTask.getTaskId().equals(ID)){
                taskVO.setJoined(true);
                break;
            }
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",taskVO);
    }

    @Override
    public ResultVO<TaskVO> getTaskByTaskId(Integer ID) {
        if(getTaskByID(ID)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",getTaskByID(ID));
    }
    private TaskVO setMemberNum(Task po){
        TaskVO taskVO = toTaskVO(po);
        if(getMemberNum(po.getId()).getCode()==CONST.REQUEST_SUCCESS){
            taskVO.setNowMembers(getMemberNum(po.getId()).getData());
        }
        return taskVO;
    }

    @Override
    public boolean deleteAllTasksByProject(Integer project_id) {
        for(Task task:taskMapper.selectByProject(project_id)){
            taskMapper.deleteByPrimaryKey(task.getId());
        }
        return getALlTasksByProject(project_id).size()==0;
    }
    private TaskVO toTaskVO(Task task){
        TaskVO taskVO = new TaskVO(task);
        if(deviceService.getDeviceById(taskVO.getDeviceId())!=null)
            taskVO.setDeviceInfo(deviceService.getDeviceById(taskVO.getDeviceId()).getDeviceInfo());
        if(typeMapper.selectByPrimaryKey(taskVO.getType())!=null)
            taskVO.setTypeInfo(typeMapper.selectByPrimaryKey(taskVO.getType()).getTypeInfo());
        return taskVO;
    }
}
