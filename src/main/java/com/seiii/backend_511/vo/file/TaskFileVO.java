package com.seiii.backend_511.vo.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.file.TaskFile;
import lombok.NonNull;

import java.util.Date;

public class TaskFileVO {
    private Integer id;

    private Integer taskId;

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    public TaskFileVO(@NonNull TaskFile taskFile) {
        this.id = taskFile.getId();
        this.taskId = taskFile.getTaskId();
        this.name = taskFile.getName();
        this.type = taskFile.getType();
        this.resourceDir = taskFile.getResourceDir();
        this.createTime = taskFile.getCreateTime();
    }
}