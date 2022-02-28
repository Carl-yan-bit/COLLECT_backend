package com.seiii.backend_511.po.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.vo.project.ProjectVO;
import lombok.Data;

import java.util.Date;
@Data
public class Project {
    private Integer id;

    private Integer userId;

    private String name;

    private String type;

    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date testTime;

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
    public Project(){

    }
    public Project(ProjectVO projectVO){
        id = projectVO.getId();
        userId = projectVO.getUserId();
        name = projectVO.getName();
        type = projectVO.getType();
        description = projectVO.getDescription();
        testTime = projectVO.getTestTime();
        this.workerAmount = projectVO.getWorkerAmount();
        this.createTime = projectVO.getCreateTime();
    }
}