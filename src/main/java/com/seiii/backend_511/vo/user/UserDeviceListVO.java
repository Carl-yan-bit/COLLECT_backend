package com.seiii.backend_511.vo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDeviceListVO {
    private List<UserDeviceVO> list;
    public UserDeviceListVO(List<UserDeviceVO> list){
        this.list = list;
    }
}
