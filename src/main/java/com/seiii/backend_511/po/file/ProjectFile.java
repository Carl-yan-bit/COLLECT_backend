package com.seiii.backend_511.po.file;

import com.seiii.backend_511.vo.file.ProjectFileVO;

import java.util.Date;

public class ProjectFile {
    private Integer id;

    private Integer projectId;

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
    public ProjectFile(ProjectFileVO projectFileVO) {
        this.id = projectFileVO.getId();
        this.projectId = projectFileVO.getProjectId();
        this.name = projectFileVO.getName();
        this.type = projectFileVO.getType();
        this.resourceDir = projectFileVO.getResourceDir();
        this.createTime = projectFileVO.getCreateTime();
    }
}