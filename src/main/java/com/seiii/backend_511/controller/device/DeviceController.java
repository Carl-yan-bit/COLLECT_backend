package com.seiii.backend_511.controller.device;

import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.service.device.DeviceService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.DeviceVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Resource
    DeviceService deviceService;

    @GetMapping("/all")
    public List<Device> getAllDevices(){
        return deviceService.getAllDevices();
    }
    @PostMapping("/add")
    public ResultVO<DeviceVO> addNewDevice(@RequestBody DeviceVO deviceVO){
        return deviceService.addNewDevice(deviceVO);
    }
}
