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

    /**
     * 创建task
     * POST: /task
     * @param taskVO
     * @return
     */
    @PostMapping("")
    public ResultVO<TaskVO> create(@RequestBody TaskVO taskVO){
        return taskService.createTask(taskVO);
    }

    /**
     * 更新task
     * PUT: /task
     * @param taskVO
     * @return
     */
    @PutMapping("")
    public ResultVO<TaskVO> update(@RequestBody TaskVO taskVO){
        return taskService.updateTask(taskVO);
    }

    /**
     * 删除task
     * DELETE: /task/{taskId}
     * @param taskId
     * @return
     */
    @DeleteMapping("/{taskId}")
    public ResultVO<TaskVO> delete(@PathVariable Integer taskId){
        return taskService.deleteTask(taskId);
    }

    /**
     * 获取task
     * GET: /task/{taskId}?uid={uid}
     * @param taskId
     * @return
     */
    @GetMapping("/{taskId}")
    public ResultVO<TaskVO> getTaskById(@PathVariable Integer taskId, @RequestParam(required = false, defaultValue = "-1") Integer uid){
        if(uid>0)return taskService.getTaskByIdAndUid(taskId,uid);
        else return taskService.getTaskByTaskId(taskId);
    }

    /**
     * 加入task
     * POST: /task/join
     * @param userTaskVO
     * @return
     */
    @PostMapping("/join")
    public ResultVO<TaskVO> joinTask(@RequestBody UserTaskVO userTaskVO){
        return taskService.joinTask(userTaskVO);
    }

    /**
     * 退出task
     * POST: /task/quit
     * @param userTaskVO
     * @return
     */
    @PostMapping("/quit")
    public ResultVO<TaskVO> quitTask(@RequestBody UserTaskVO userTaskVO){
        return taskService.quitTask(userTaskVO);
    }

    /**
     * 获取project下所有task
     * GET: /task?isActive={isActive}&project={projectId}&uid={uid}&page={page}
     */
    @GetMapping("")
    public PageInfo<TaskVO> getTaskByProject(@RequestParam(required = false, defaultValue = "false") Boolean isActive,
                                             @RequestParam(required = false, defaultValue = "-1") Integer projectId,
                                             @RequestParam(required = false, defaultValue = "-1") Integer uid,
                                             @RequestParam(required = false, defaultValue = "1") Integer page){
        if(projectId<0&&isActive&&uid>0){
            return taskService.getNowTasks(uid,page);//获取用户参与的所有可用task
        }
        else if(isActive){
            return taskService.getActiveTaskByProject(projectId,page);//获取project下所有可用的task
        }
        else if(uid>0){
            return taskService.getTaskByProjectWithUID(projectId,uid,page);//获取project下所有task(带uid)
        }
        else return taskService.getTaskByProject(projectId, page);
    }

//    @GetMapping("/find/user/now/{pid}")
//    public PageInfo<TaskVO> getNowTasks(@RequestParam Integer uid,@PathVariable Integer pid){
//
//    }
//    @GetMapping("/find/user/{pid}")
//    public PageInfo<TaskVO> getTasksByProjectWithUid(@RequestParam Integer project_id,@RequestParam Integer uid,@PathVariable Integer pid){
//        return taskService.getTaskByProjectWithUID(project_id,uid,pid);
//    }


//    @GetMapping("/find/active/{pid}")
//    public PageInfo<TaskVO> getActiveTasksByProject(@RequestParam Integer project_id,@PathVariable Integer pid){
//        return taskService.getActiveTaskByProject(project_id,pid);
//    }

    /**
     * 获取用户参加过的历史task
     * GET: /task/history?uid={uid}&page={page}
     * @param uid
     * @param page
     * @return
     */
    @GetMapping("/history")
    public PageInfo<TaskVO> getHistoryTasks(@RequestParam Integer uid,@RequestParam Integer page){
        return taskService.getHistoryTasks(uid,page);
    }

    /**
     * 获取用户需要撰写报告的task
     * GET: /task/todoTask?uid={uid}&page={page}
     * @param uid
     * @param pid
     * @return
     */
    @GetMapping("/todoTask")
    public PageInfo<TaskVO> getToDoTasks(@RequestParam Integer uid,@RequestParam Integer page){
        return taskService.getTodoTasks(uid,page);
    }

    /**
     * 获取需求的成员人数
     * GET: /task/{taskId}/memberNum
     * @param taskId
     * @return
     */
    @GetMapping("/{taskId}/memberNum")
    public ResultVO<Integer> getMemberNum(@PathVariable Integer taskId){
        return taskService.getMemberNum(taskId);
    }


}
