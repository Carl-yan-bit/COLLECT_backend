package com.seiii.backend_511.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.user.User;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class UserVO {
    private Integer id;

    private Integer exp;

    private Integer level;

    private Integer projectPreferenceId;

    private Integer activity;

    private String name;

    private String email;

    private String phonenumber;

    private String password;

    private String userRole;

    private Date createTime;
    public UserVO(@NonNull User user) {
        this.id = user.getId();
        this.exp = user.getExp();
        this.level = user.getLevel();
        this.projectPreferenceId = user.getProjectPreferenceId();
        this.activity = user.getActivity();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phonenumber = user.getPhonenumber();
        this.password = user.getPassword();
        this.userRole = user.getUserRole();
        this.createTime = user.getCreateTime();
    }

    public UserVO() {
    }
}