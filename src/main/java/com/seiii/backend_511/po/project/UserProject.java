package com.seiii.backend_511.po.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.vo.project.UserProjectVO;
import lombok.Data;

import java.util.Date;

@Data
public class UserProject {
    private Integer id;

    private Integer projectId;

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
    public UserProject(){

    }
    public UserProject(UserProjectVO userProject){
        id = userProject.getId();
        projectId = userProject.getProjectId();
        userId = userProject.getUserId();
        joinTime = userProject.getJoinTime();
    }
}