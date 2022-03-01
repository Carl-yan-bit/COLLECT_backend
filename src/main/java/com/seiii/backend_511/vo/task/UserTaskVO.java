package com.seiii.backend_511.vo.task;

import com.seiii.backend_511.po.task.UserTask;
import lombok.Data;

@Data
public class UserTaskVO {
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
    public UserTaskVO(){}
    public UserTaskVO(UserTask userTask){
        id = userTask.getId();
        taskId = userTask.getTaskId();
        userId = userTask.getUserId();
    }
}