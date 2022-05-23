package com.seiii.backend_511.unit.service;

import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.Encryption;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.UserFormVo;
import com.seiii.backend_511.vo.user.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    public void testRegister(){
        UserVO user = new UserVO();

        user.setPassword(Encryption.encryptPassword("123456",user.getName()));
        user.setUserRole(CONST.USER_ROLE_ROOT);
        user.setCreateTime(new Date());
        user.setEmail("19125018@smail.nju.edu.cn");
        user.setPhonenumber("15109175289");
        ResultVO<UserVO> ans= userService.userRegister(user);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //表单不能少任何一项
        user.setName("孙钰昇");
        ans= userService.userRegister(user);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testLogin(){
        ResultVO<UserVO> ans= userService.userLogin("123456789","1234536");
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        ans= userService.userLogin("123456789","123456");
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testChangePassWord(){
        ResultVO<UserVO> ans;
        testLogin();
        ans= userService.userChangePassword("123456789","1234567","1234568");
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //输错密码不能更改
        ans= userService.userChangePassword("123456789","123456","1234567");
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
        //输对密码才能更改
        ans= userService.userLogin("123456789","1234567");
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testGetUserByID(){
        ResultVO<UserVO> ans;
        ans = userService.getUserByUidWithCode(19);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        ans = userService.getUserByUidWithCode(1);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testGetAttribute(){
        userService.getUserAttribute(2);
    }
}
