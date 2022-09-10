package com.seiii.backend_511.vo.user;

import lombok.Data;

@Data
public class UserAttributeVO {

    private int capability;
    private String preference;
    private int activity;
    private int assistance;
    private int examination;
    private int reportPoint;
    private int discovery;
    private int taskDifficulty;
    private double totalScore;

    public UserAttributeVO(){

    }

    public UserAttributeVO(int capability,
                           String preference,
                           int activity,
                           int assistance,
                           int examination,
                           int reportPoint,
                           int discovery,
                           int taskDifficulty,
                           double totalScore){
        this.capability=capability;
        this.preference=preference;
        this.activity=activity;
        this.assistance=assistance;
        this.examination=examination;
        this.reportPoint=reportPoint;
        this.discovery=discovery;
        this.taskDifficulty=taskDifficulty;
        this.totalScore=totalScore;
    }

    public boolean isVaild(){
        return capability>=0 &&
                preference!=null &&
                activity>=0 &&
                assistance>=0 &&
                examination>=0 &&
                reportPoint>=0 &&
                totalScore>=0;
    }
}
