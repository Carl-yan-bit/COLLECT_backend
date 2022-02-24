package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.vo.USER;
import java.util.List;

public interface USERMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(USER record);

    USER selectByPrimaryKey(Integer id);

    List<USER> selectAll();

    int updateByPrimaryKey(USER record);
}