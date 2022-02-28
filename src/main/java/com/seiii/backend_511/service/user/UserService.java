package com.seiii.backend_511.service.user;

import com.seiii.backend_511.po.user.User;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.UserVO;

import java.util.List;

public interface UserService {
    //用户注册
    ResultVO<UserVO> userRegister(UserVO user);
    //用户登录
    ResultVO<UserVO> userLogin(String user_idx,String password);
    //用户修改密码
    ResultVO<UserVO> userChangePassword(String user_idx,String password_old,String password_new);
    UserVO getUserByUid(Integer uid);
    List<User> getAll();
}
