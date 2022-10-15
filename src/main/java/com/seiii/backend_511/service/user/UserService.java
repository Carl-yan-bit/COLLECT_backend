package com.seiii.backend_511.service.user;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.po.user.User;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.*;

import java.util.List;

public interface UserService {
    //用户注册
    ResultVO<UserVO> userRegister(UserVO user);
    //用户登录
    ResultVO<UserVO> userLogin(String user_idx,String password);
    //用户修改密码
    ResultVO<UserVO> userChangePassword(String user_idx,String password_old,String password_new);
    UserVO getUserByUid(Integer uid);
    ResultVO<UserVO> getUserByUidWithCode(Integer uid);
    List<User> getAll();
    ResultVO<List<Device>> getUserDevices(Integer uid);
    ResultVO<DeviceVO> addUserDevice(UserDeviceVO userDeviceVO);
    ResultVO<List<DeviceVO>> addUserDevice(UserDeviceListVO userDeviceVOList);
    ResultVO<DeviceVO> deleteUserDevice(UserDeviceVO userDeviceVO);
    void getExp(UserVO userVO, int Exp);
    ResultVO<UserAttributeVO> getUserAttribute(Integer uid);
    UserAttributeVO getUserAttributeWithoutScore(Integer uid);
    //获取所有用户信息
    ResultVO<List<UserVO>> getAllUsers();
}
