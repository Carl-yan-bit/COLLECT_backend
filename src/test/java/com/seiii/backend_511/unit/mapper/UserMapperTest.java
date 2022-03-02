package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.UserMapper;
import com.seiii.backend_511.po.user.User;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.Encryption;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class UserMapperTest {
    @Resource
    UserMapper userMapper;

    @Test
    public void testInsert() throws Exception{
        User user = new User();
        user.setName("孙钰昇");
        user.setPassword(Encryption.encryptPassword("123456",user.getName()));
        user.setUserRole(CONST.USER_ROLE_ROOT);
        user.setCreateTime(new Date());
        user.setEmail("191250128@smail.nju.edu.cn");
        user.setPhonenumber("15009175289");
        Assert.assertEquals(1,userMapper.insert(user));
    }
    @Test
    public void testDelete() throws Exception{
        Assert.assertEquals(1,userMapper.deleteByPrimaryKey(1));
    }
}
