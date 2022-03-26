package com.seiii.backend_511.po.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.vo.project.ProjectVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class Project {
    private Integer id;
    private Integer clickTimes;
    private Integer userId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
    public Project(ProjectVO projectVO){
        id = projectVO.getId();
        this.clickTimes = projectVO.getClickTimes();
        userId = projectVO.getUserId();
        name = projectVO.getName();
        state = projectVO.getState();
        difficulty = projectVO.getDifficulty();
        deviceId = projectVO.getDeviceId();
        type = projectVO.getType();
        description = projectVO.getDescription();
        testTime = projectVO.getTestTime();
        this.workerAmount = projectVO.getWorkerAmount();
        this.createTime = projectVO.getCreateTime();
    }
}