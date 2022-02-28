package com.seiii.backend_511.vo.project;

import com.seiii.backend_511.po.project.UserProject;
import lombok.Data;

@Data
public class UserProjectVO {
    private Integer id;

    private Integer projectId;

    private Integer userId;

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
    }

}