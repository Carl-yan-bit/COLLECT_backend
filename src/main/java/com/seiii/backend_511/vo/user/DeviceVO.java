package com.seiii.backend_511.vo.user;

import com.seiii.backend_511.po.user.Device;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class DeviceVO {
    private Integer id;
    private Integer uid;
    private Integer deviceId;
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
    public DeviceVO(@NonNull Device device){
        id = device.getId();
        deviceInfo = device.getDeviceInfo();
    }

}
