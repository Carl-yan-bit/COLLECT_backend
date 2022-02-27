package com.seiii.backend_511.controller.user;


import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.UserFormVo;
import com.seiii.backend_511.vo.user.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
