package com.seiii.backend_511.po.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.vo.report.ReportVO;
import lombok.Data;

import java.util.Date;
@Data
public class Report {
    private Integer id;

    private Integer taskId;

    private Integer userId;

    private Integer parentReport;

    private Float score;

    private String name;

    private String state;

    private String description;

    private String testStep;

    private Integer deviceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    public Report(){}


    public Report(ReportVO reportVO) {
        this.id = reportVO.getId();
        this.taskId = reportVO.getTaskId();
        this.userId = reportVO.getUserId();
        this.parentReport = reportVO.getParentReport();
        this.score = reportVO.getScore();
        this.name = reportVO.getName();
        this.state = reportVO.getState();
        this.description = reportVO.getDescription();
        this.testStep = reportVO.getTestStep();
        this.deviceId = reportVO.getDeviceId();
        this.createTime = reportVO.getCreateTime();

    }
}