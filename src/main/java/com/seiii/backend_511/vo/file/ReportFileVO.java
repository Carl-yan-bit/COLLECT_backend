package com.seiii.backend_511.vo.file;

import com.seiii.backend_511.po.file.ReportFile;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class ReportFileVO {
    private Integer id;

    private Integer reportId;

    private String name;

    private String type;

    private String resourceDir;

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

    public ReportFileVO(@NonNull ReportFile reportFile) {
        this.id = reportFile.getId();
        this.reportId = reportFile.getReportId();
        this.name = reportFile.getName();
        this.type = reportFile.getType();
        this.resourceDir = reportFile.getResourceDir();
        this.createTime = reportFile.getCreateTime();
    }
}