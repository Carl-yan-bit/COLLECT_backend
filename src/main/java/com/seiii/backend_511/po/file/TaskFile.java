package com.seiii.backend_511.po.file;

import com.seiii.backend_511.vo.file.FileVO;
import com.seiii.backend_511.vo.file.TaskFileVO;
import lombok.Data;

import java.util.Date;
@Data
public class TaskFile {
    private Integer id;

    private Integer taskId;

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
    public TaskFile(TaskFileVO taskFileVO) {
        this.id = taskFileVO.getId();
        this.taskId = taskFileVO.getTaskId();
        this.name = taskFileVO.getName();
        this.type = taskFileVO.getType();
        this.resourceDir = taskFileVO.getResourceDir();
        this.createTime = taskFileVO.getCreateTime();
    }

    public TaskFile(FileVO FileVO) {
        this.id = FileVO.getId();
        this.taskId = FileVO.getCarrierId();
        this.name = FileVO.getName();
        this.type = FileVO.getType();
        this.resourceDir = FileVO.getResourceDir();
        this.createTime = FileVO.getCreateTime();
    }
}