package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.UserLog;
import java.util.List;

public interface UserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    List<UserLog> selectAll();

    int updateByPrimaryKey(UserLog record);
}