package com.seiii.backend_511.po.user;

import com.seiii.backend_511.vo.user.UserDeviceVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserDevice {
    private Integer id;

    private Integer userId;

    private Integer deviceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
    public UserDevice(UserDeviceVO userDevice) {
        this.id = userDevice.getId();
        this.userId = userDevice.getUserId();
        this.deviceId = userDevice.getDeviceId();
    }
}