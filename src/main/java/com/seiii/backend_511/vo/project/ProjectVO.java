package com.seiii.backend_511.vo.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.project.Project;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data

public class ProjectVO {
    private Integer id;

    private Integer userId;

    private String name;

    private String type;
    private Integer memberNum;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date testTime;
    private boolean joined = false;

    private Integer workerAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public Integer getWorkerAmount() {
        return workerAmount;
    }

    public void setWorkerAmount(Integer workerAmount) {
        this.workerAmount = workerAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public ProjectVO(){

    }

    public ProjectVO(@NonNull Project project) {
        this.id = project.getId();
        this.userId = project.getUserId();
        this.name = project.getName();
        this.type = project.getType();
        this.description = project.getDescription();
        this.testTime = project.getTestTime();
        this.workerAmount = project.getWorkerAmount();
        this.createTime = project.getCreateTime();
    }
}