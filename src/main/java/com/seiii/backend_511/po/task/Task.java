package com.seiii.backend_511.po.task;

import com.seiii.backend_511.vo.task.TaskVO;

import java.util.Date;

public class Task {
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
    public Task(TaskVO taskVO) {
        this.id = taskVO.getId();
        this.projectId = taskVO.getProjectId();
        this.name = taskVO.getName();
        this.state = taskVO.getState();
        this.description = taskVO.getDescription();
        this.testTime = taskVO.getTestTime();
        this.workerAmount = taskVO.getWorkerAmount();
        this.createTime = taskVO.getCreateTime();
    }
}