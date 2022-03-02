package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.UserProjectMapper;
import com.seiii.backend_511.po.project.UserProject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class UserProjectMapperTest {
    @Resource
    UserProjectMapper userProjectMapper;

    @Test
    public void testDelete(){
        Assert.assertEquals(1,userProjectMapper.deleteByPrimaryKey(1));
    }
    @Test
    public void testInsert(){
        UserProject userProject = new UserProject();
        userProject.setProjectId(2);
        userProject.setUserId(1);
        Assert.assertEquals(1,userProjectMapper.insert(userProject));
    }
    @Test
    public void testUpdate(){
        UserProject userProject = new UserProject();
        userProject.setId(1);
        userProject.setProjectId(2);
        userProject.setUserId(3);
        Assert.assertEquals(1,userProjectMapper.updateByPrimaryKey(userProject));
    }
    @Test
    public void testSelectByPrimaryKey(){
        Assert.assertEquals(Integer.valueOf(2),userProjectMapper.selectByPrimaryKey(1).getProjectId());
    }
    @Test
    public void testSelectByUID(){
        Assert.assertEquals(1,userProjectMapper.selectByUser(2).size());
    }
    @Test
    public void testSelectByProjectId(){
        Assert.assertEquals(0,userProjectMapper.selectByProjects(1).size());
    }
}
