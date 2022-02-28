package com.seiii.backend_511.vo.task;

import com.seiii.backend_511.po.task.Task;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class TaskVO {
    private Integer id;

    private Integer projectId;

    private String name;

    private String state;

    private String description;

    private Date testTime;

    private Integer workerAmount;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public Integer getWorkerAmount() {
        return workerAmount;
    }

    public void setWorkerAmount(Integer workerAmount) {
        this.workerAmount = workerAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public TaskVO(@NonNull Task task) {
        this.id = task.getId();
        this.projectId = task.getProjectId();
        this.name = task.getName();
        this.state = task.getState();
        this.description = task.getDescription();
        this.testTime = task.getTestTime();
        this.workerAmount = task.getWorkerAmount();
        this.createTime = task.getCreateTime();
    }
}