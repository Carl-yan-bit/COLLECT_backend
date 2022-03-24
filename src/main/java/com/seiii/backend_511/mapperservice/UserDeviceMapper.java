package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.po.user.UserDevice;
import java.util.List;

public interface UserDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDevice record);

    UserDevice selectByPrimaryKey(Integer id);
    UserDevice selectByUserAndDevice(Integer uid,Integer did);
    List<UserDevice> selectAll();
    List<Device> selectByUserId(Integer uid);
    int updateByPrimaryKey(UserDevice record);
}