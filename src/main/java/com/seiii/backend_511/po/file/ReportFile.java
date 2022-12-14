package com.seiii.backend_511.po.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.report.Report;
import com.seiii.backend_511.vo.file.FileVO;
import com.seiii.backend_511.vo.file.ReportFileVO;
import lombok.Data;

import java.util.Date;
@Data
public class ReportFile {
    private Integer id;

    private Integer reportId;

    private String name;

    private String type;

    private String resourceDir;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir == null ? null : resourceDir.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public ReportFile(ReportFileVO reportFileVO) {
        this.id = reportFileVO.getId();
        this.reportId = reportFileVO.getReportId();
        this.name = reportFileVO.getName();
        this.type = reportFileVO.getType();
        this.resourceDir = reportFileVO.getResourceDir();
        this.createTime = reportFileVO.getCreateTime();
    }

    public ReportFile(FileVO FileVO) {
        this.id = FileVO.getId();
        this.reportId = FileVO.getCarrierId();
        this.name = FileVO.getName();
        this.type = FileVO.getType();
        this.resourceDir = FileVO.getResourceDir();
        this.createTime = FileVO.getCreateTime();
    }

    public ReportFile(){

    }
}