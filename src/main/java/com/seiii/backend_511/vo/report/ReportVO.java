package com.seiii.backend_511.vo.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.report.Report;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class ReportVO {
    private Integer id;

    private Integer taskId;

    private Integer userId;

    private String name;

    private String state;

    private String description;

    private String testStep;

    private String deviceInfo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer gettaskId() {
        return taskId;
    }

    public void settaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getTestStep() {
        return testStep;
    }

    public void setTestStep(String testStep) {
        this.testStep = testStep == null ? null : testStep.trim();
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo == null ? null : deviceInfo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public ReportVO(){}
    public ReportVO(@NonNull Report report) {
        this.id = report.getId();
        this.taskId = report.gettaskId();
        this.userId = report.getUserId();
        this.name = report.getName();
        this.state = report.getState();
        this.description = report.getDescription();
        this.testStep = report.getTestStep();
        this.deviceInfo = report.getDeviceInfo();
        this.createTime = report.getCreateTime();
    }
}