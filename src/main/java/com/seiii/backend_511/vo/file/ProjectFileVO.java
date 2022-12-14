package com.seiii.backend_511.vo.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.file.ProjectFile;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class ProjectFileVO {
    private Integer id;

    private Integer projectId;

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

    public ProjectFileVO(@NonNull ProjectFile projectfile) {
        this.id = projectfile.getId();
        this.projectId = projectfile.getProjectId();
        this.name = projectfile.getName();
        this.type = projectfile.getType();
        this.resourceDir = projectfile.getResourceDir();
        this.createTime = projectfile.getCreateTime();
    }
}