package com.seiii.backend_511.controller.user;


import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ResultVO<UserVO> register(@RequestBody UserVO userVO){
        return userService.userRegister(userVO);
    }
    @PostMapping("/login")
    public ResultVO<UserVO> login(@RequestBody UserFormVo userFormVo){
        return userService.userLogin(userFormVo.getUser_idx(),userFormVo.getPassword());
    }
    @PostMapping("/changePassword")
    public ResultVO<UserVO> changePassword(@RequestBody UserFormVo userFormVo){
        return userService.userChangePassword(userFormVo.getUser_idx(), userFormVo.getPassword(), userFormVo.getPassword_new());
    }
    @GetMapping("/find/{uid}")
    public ResultVO<UserVO> getUserByUid(@PathVariable Integer uid){
        return userService.getUserByUidWithCode(uid);
    }
    @PostMapping("/device/add")
    public ResultVO<List<DeviceVO>> addUserDevice(@RequestBody UserDeviceListVO userDeviceVOList){
        return userService.addUserDevice(userDeviceVOList);
    }
    @PostMapping("/device/delete")
    public ResultVO<DeviceVO> deleteUserDevice(@RequestBody UserDeviceVO userDeviceVO){
        return userService.deleteUserDevice(userDeviceVO);
    }
    @GetMapping("/device")
    public ResultVO<List<Device>> getUserDevices(@RequestParam Integer uid){
        return userService.getUserDevices(uid);
    }
    @GetMapping("/attribute")
    public ResultVO<UserAttributeVO> getUserAttribute(@RequestParam Integer uid){
        return userService.getUserAttribute(uid);
    }
}
