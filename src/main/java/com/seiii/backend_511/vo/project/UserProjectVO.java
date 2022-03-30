package com.seiii.backend_511.vo.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.project.UserProject;
import lombok.Data;

import java.util.Date;

@Data
public class UserProjectVO {
    private Integer id;

    private Integer projectId;
    private double rank = 0.0;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date joinTime;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public UserProjectVO(){

    }
    public UserProjectVO(UserProject userProject){
        id = userProject.getId();
        projectId = userProject.getProjectId();
        userId = userProject.getUserId();
        joinTime = userProject.getJoinTime();
    }

}