package com.seiii.backend_511.controller.user;


import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * POST: /user
     * @param userVO
     * @return
     */
    @PostMapping("")
    public ResultVO<UserVO> register(@RequestBody UserVO userVO){
        return userService.userRegister(userVO);
    }

    /**
     * 用户登录
     * POST: /user/register
     * @param userFormVo
     * @return
     */
    @PostMapping("/login")
    public ResultVO<UserVO> login(@RequestBody UserFormVo userFormVo){
        return userService.userLogin(userFormVo.getUser_idx(),userFormVo.getPassword());
    }

//    /**
//     * 更改密码
//     * PUT: /{id}/password
//     * @param userFormVo
//     * @return
//     */
//    @PutMapping("/password")
//    public ResultVO<UserVO> changePassword(@PathVariable String idx, @RequestBody UserFormVo userFormVo){
//        return userService.userChangePassword(idx, userFormVo.getPassword(), userFormVo.getPassword_new());
//    }

    /**
     * 根据用户ID获取用户信息
     * GET: /user/{uid}
     * @param uid
     * @return 用户信息
     */
    @GetMapping("/{uid}")
    public ResultVO<UserVO> getUserByUid(@PathVariable Integer uid){
        return userService.getUserByUidWithCode(uid);
    }

    /**
     * 设置用户设备
     * PUT: /user/{uid}/device
     * @param userDeviceVOList
     * @return
     */
    @PutMapping("/{uid}/device")
    public ResultVO<List<DeviceVO>> addUserDevice(@RequestBody UserDeviceListVO userDeviceVOList){
        return userService.addUserDevice(userDeviceVOList);
    }


//    @PostMapping("/device/delete")
//    public ResultVO<DeviceVO> deleteUserDevice(@RequestBody UserDeviceVO userDeviceVO){
//        return userService.deleteUserDevice(userDeviceVO);
//    }

    /**
     * 获取用户所有设备
     * GET: /user/{uid}/device
     * @param uid
     * @return
     */
    @GetMapping("/{uid}/device")
    public ResultVO<List<Device>> getUserDevices(@PathVariable Integer uid){
        return userService.getUserDevices(uid);
    }

    /**
     * 获取用户属性
     * GET: /user/{uid}/attribute
     * @param uid
     * @return
     */
    @GetMapping("/{uid}/attribute")
    public ResultVO<UserAttributeVO> getUserAttribute(@PathVariable Integer uid){
        return userService.getUserAttribute(uid);
    }
}
