package com.seiii.backend_511.po.report;

import com.seiii.backend_511.vo.report.ReportVO;
import lombok.Data;

import java.util.Date;
@Data
public class Report {
    private Integer id;

    private Integer projectId;

    private Integer userId;

    private String name;

    private String state;

    private String description;

    private String testStep;

    private String deviceInfo;

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
    public Report(ReportVO reportVO) {
        this.id = reportVO.getId();
        this.projectId = reportVO.getProjectId();
        this.userId = reportVO.getUserId();
        this.name = reportVO.getName();
        this.state = reportVO.getState();
        this.description = reportVO.getDescription();
        this.testStep = reportVO.getTestStep();
        this.deviceInfo = reportVO.getDeviceInfo();
        this.createTime = reportVO.getCreateTime();
    }
}