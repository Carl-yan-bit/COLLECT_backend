package com.seiii.backend_511.po.user;

import com.seiii.backend_511.vo.user.UserVO;

import java.util.Date;

public class User {
    private Integer id;

    private String name;

    private String email;

    private String phonenumber;

    private String password;

    private String userRole;

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
    public User(UserVO userVO) {
        this.id = userVO.getId();
        this.name = userVO.getName();
        this.email = userVO.getEmail();
        this.phonenumber = userVO.getPhonenumber();
        this.password = userVO.getPassword();
        this.userRole = userVO.getUserRole();
        this.createTime = userVO.getCreateTime();
    }
}