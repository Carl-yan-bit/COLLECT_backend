package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.UserTaskMapper;
import com.seiii.backend_511.po.task.UserTask;
import com.seiii.backend_511.vo.task.UserTaskVO;
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
public class UserTaskMapperTest {
    @Resource
    UserTaskMapper userTaskMapper;

    @Test
    public void testDelete(){
        Assert.assertEquals(1,userTaskMapper.deleteByPrimaryKey(1));
    }
    @Test
    public void testInsert(){
        UserTask userTask = new UserTask();
        userTask.setTaskId(2);
        userTask.setUserId(1);
        Assert.assertEquals(1,userTaskMapper.insert(userTask));
    }
    @Test
    public void testUpdate(){
        UserTask userTask = new UserTask();
        userTask.setId(1);
        userTask.setTaskId(1);
        userTask.setUserId(2);
        Assert.assertEquals(1,userTaskMapper.updateByPrimaryKey(userTask));
    }
    @Test
    public void testSelectByPrimaryKey(){
        Assert.assertEquals(Integer.valueOf(2),userTaskMapper.selectByPrimaryKey(2).getTaskId());
    }
    @Test
    public void testSelectByUID(){
        Assert.assertEquals(2,userTaskMapper.selectByUid(3).size());
    }
    @Test
    public void testSelectByTaskId(){
        Assert.assertEquals(6,userTaskMapper.selectByTask(1).size());
    }
}
