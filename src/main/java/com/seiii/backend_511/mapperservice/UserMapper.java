package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.user.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();
    List<User> selectUserWithProject();
    int updateByPrimaryKey(User record);
    User selectByName(String name);
    User selectByEmail(String email);
    User selectByPhoneNumber(String phoneNumber);
}