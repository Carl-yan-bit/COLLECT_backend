package com.seiii.backend_511.po.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.vo.task.TaskVO;
import lombok.Data;

import java.util.Date;
@Data
public class Task {
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
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Task(){}
    public Task(TaskVO taskVO) {
        this.id = taskVO.getId();
        this.projectId = taskVO.getProjectId();
        this.name = taskVO.getName();
        this.state = taskVO.getState();
        this.description = taskVO.getDescription();
        this.testTime = taskVO.getTestTime();
        this.workerAmount = taskVO.getWorkerAmount();
        this.createTime = taskVO.getCreateTime();
        this.difficulty = taskVO.getDifficulty();
        this.deviceId = taskVO.getDeviceId();
        this.type = taskVO.getType();
    }
}