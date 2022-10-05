package com.seiii.backend_511.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class UserLog {
    private Integer id;

    private Integer userId;

    private String msg;

    private Integer activityPoint;

    private Date time;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Integer getActivityPoint() {
        return activityPoint;
    }

    public void setActivityPoint(Integer activityPoint) {
        this.activityPoint = activityPoint;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public UserLog(Integer userId, String msg, Integer activityPoint, Date time) {
        this.userId = userId;
        this.msg = msg;
        this.activityPoint = activityPoint;
        this.time = time;
    }
}