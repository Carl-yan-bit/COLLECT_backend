package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.user.Device;
import java.util.List;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    Device selectByPrimaryKey(Integer id);

    List<Device> selectAll();

    int updateByPrimaryKey(Device record);
}