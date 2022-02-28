package com.seiii.backend_511.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.user.User;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class UserVO {
    private Integer id;

    private String name;

    private String email;

    private String phonenumber;

    private String password;

    private String userRole;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public UserVO(@NonNull User user) {
        this.id = user.getId();
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