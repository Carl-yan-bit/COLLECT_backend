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

    private Integer parentReport;
    private String userName;
    private String deviceInfo;
    private Float score;

    private String name;

    private String state;

    private String description;

    private String testStep;

    private Integer deviceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public ReportVO(){}
    public ReportVO(@NonNull Report report) {
        this.id = report.getId();
        this.taskId = report.getTaskId();
        this.userId = report.getUserId();
        this.parentReport = report.getParentReport();
        this.score = report.getScore();
        this.name = report.getName();
        this.state = report.getState();
        this.description = report.getDescription();
        this.testStep = report.getTestStep();
        this.deviceId = report.getDeviceId();
        this.createTime = report.getCreateTime();
    }
}