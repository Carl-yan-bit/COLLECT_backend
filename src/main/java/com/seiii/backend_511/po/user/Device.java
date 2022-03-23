package com.seiii.backend_511.po.user;

import com.seiii.backend_511.vo.user.DeviceVO;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Device {
    private Integer id;

    private String deviceInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo == null ? null : deviceInfo.trim();
    }
    public Device(DeviceVO device){
        id = device.getId();
        deviceInfo = device.getDeviceInfo();
    }
}