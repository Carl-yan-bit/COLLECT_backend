package com.seiii.backend_511.service.device;

import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.DeviceVO;

import java.util.List;

public interface DeviceService {
    DeviceVO getDeviceById(Integer id);
    ResultVO<DeviceVO> addNewDevice(DeviceVO deviceVO);
    List<Device> getAllDevices();
}
