package com.seiii.backend_511.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.task.Task;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class TaskVO {
    private Integer id;

    private Integer projectId;

    private Integer difficulty;

    private Integer deviceId;

    private Integer type;

    private String name;

    private String state;

    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date testTime;

    private Integer workerAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private boolean joined = false;
    private boolean finished = false;
    private int nowMembers;
    public TaskVO(){}
    public TaskVO(@NonNull Task task) {
        this.id = task.getId();
        this.projectId = task.getProjectId();
        this.name = task.getName();
        this.state = task.getState();
        this.description = task.getDescription();
        this.testTime = task.getTestTime();
        this.workerAmount = task.getWorkerAmount();
        this.createTime = task.getCreateTime();
        this.difficulty = task.getDifficulty();
        this.deviceId = task.getDeviceId();
        this.type = task.getType();
    }
}