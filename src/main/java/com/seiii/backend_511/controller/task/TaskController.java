package com.seiii.backend_511.controller.task;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.task.UserTaskVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskService taskService;
    @GetMapping("/find/{pid}")
    public PageInfo<TaskVO> getTaskByProject(@RequestParam Integer project_id, @PathVariable Integer pid){
        return taskService.getTaskByProject(project_id,pid);
    }
    @GetMapping("/find/active/{pid}")
    public PageInfo<TaskVO> getActiveTasksByProject(@RequestParam Integer project_id,@PathVariable Integer pid){
        return taskService.getActiveTaskByProject(project_id,pid);
    }
    @GetMapping("/find/user/history/{pid}")
    public PageInfo<TaskVO> getHistoryTasks(@RequestParam Integer uid,@PathVariable Integer pid){
        return taskService.getHistoryTasks(uid,pid);
    }
    @GetMapping("/find/user/todo/{pid}")
    public PageInfo<TaskVO> getToDoTasks(@RequestParam Integer uid,@PathVariable Integer pid){
        return taskService.getTodoTasks(uid,pid);
    }
    @GetMapping("/find/user/now/{pid}")
    public PageInfo<TaskVO> getNowTasks(@RequestParam Integer uid,@PathVariable Integer pid){
        return taskService.getNowTasks(uid,pid);
    }
    @GetMapping("/get/member/num")
    public ResultVO<Integer> getMemberNum(@RequestParam Integer taskId){
        return taskService.getMemberNum(taskId);
    }
    @PostMapping("/create")
    public ResultVO<TaskVO> create(@RequestBody TaskVO taskVO){
        return taskService.createTask(taskVO);
    }
    @PostMapping("/update")
    public ResultVO<TaskVO> update(@RequestBody TaskVO taskVO){
        return taskService.updateTask(taskVO);
    }
    @GetMapping("/delete")
    public ResultVO<TaskVO> delete(@RequestParam Integer taskId){
        return taskService.deleteTask(taskId);
    }
    @PostMapping("/join")
    public ResultVO<TaskVO> joinTask(@RequestBody UserTaskVO userTaskVO){
        return taskService.joinTask(userTaskVO);
    }
    @PostMapping("/quit")
    public ResultVO<TaskVO> quitTask(@RequestBody UserTaskVO userTaskVO){
        return taskService.quitTask(userTaskVO);
    }
}
