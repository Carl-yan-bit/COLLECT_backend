package com.seiii.backend_511.po.project;

import com.seiii.backend_511.vo.project.ProjectPreferenceVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectPreference {
    private Integer id;

    private Integer userId;

    private Float difficulty;

    private Float deviceId;

    private Float type;

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

    public Float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Float difficulty) {
        this.difficulty = difficulty;
    }

    public Float getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Float deviceId) {
        this.deviceId = deviceId;
    }

    public Float getType() {
        return type;
    }

    public void setType(Float type) {
        this.type = type;
    }

    public ProjectPreference(ProjectPreferenceVO projectPreference) {
        this.id = projectPreference.getId();
        this.userId = projectPreference.getUserId();
        this.difficulty = projectPreference.getDifficulty();
        this.deviceId = projectPreference.getDeviceId();
        this.type = projectPreference.getType();
    }
}