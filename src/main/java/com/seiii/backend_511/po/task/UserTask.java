package com.seiii.backend_511.po.task;

import com.seiii.backend_511.vo.task.UserTaskVO;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserTask {
    private Integer id;

    private Integer taskId;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public UserTask(){}
    public UserTask(@NonNull UserTaskVO userTaskVO){
        id = userTaskVO.getId();
        taskId = userTaskVO.getTaskId();
        userId = userTaskVO.getUserId();
    }
}