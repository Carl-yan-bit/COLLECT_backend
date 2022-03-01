package com.seiii.backend_511.service.task;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.task.UserTaskVO;

import java.util.List;

public interface TaskService {
    PageInfo<TaskVO> getTaskByProject(Integer project_id,Integer pid);
    PageInfo<TaskVO> getActiveTaskByProject(Integer project_id,Integer pid);
    PageInfo<TaskVO> getHistoryTasks(Integer uid,Integer pid);
    PageInfo<TaskVO> getNowTasks(Integer uid,Integer pid);
    List<TaskVO> getALlTasksByProject(Integer project_id);
    boolean deleteAllTasksByProject(Integer project_id);
    ResultVO<TaskVO> createTask(TaskVO taskVO);
    ResultVO<TaskVO> updateTask(TaskVO taskVO);
    ResultVO<TaskVO> joinTask(UserTaskVO userTaskVO);
    ResultVO<TaskVO> quitTask(UserTaskVO userTaskVO);
    ResultVO<TaskVO> deleteTask(Integer taskID);
}
