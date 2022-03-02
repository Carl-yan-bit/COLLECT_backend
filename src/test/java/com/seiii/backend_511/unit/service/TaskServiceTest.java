package com.seiii.backend_511.unit.service;

import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;

import com.seiii.backend_511.vo.task.UserTaskVO;
import com.seiii.backend_511.vo.task.TaskVO;
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
public class TaskServiceTest {
    @Resource
    TaskService taskService;

    @Test
    public void testCreateTask(){
        TaskVO taskVO = new TaskVO();
        taskVO.setProjectId(2);
        taskVO.setTestTime(new Date());
        taskVO.setCreateTime(new Date());
        taskVO.setState(CONST.STATE_OPEN);
        taskVO.setDescription("我真的不喜欢写测试");
        taskVO.setWorkerAmount(10);

        ResultVO<TaskVO> ans = taskService.createTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //缺少必要的数据
        taskVO.setName("啦啦啦德玛西亚");
        taskVO.setWorkerAmount(10000);
        ans = taskService.createTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //需求人数不能超过项目总人数
        taskVO.setProjectId(200);
        taskVO.setWorkerAmount(1);
        ans = taskService.createTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //不存在的项目
        taskVO.setProjectId(2);
        ans = taskService.createTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testUpdate(){
        TaskVO taskVO = new TaskVO();
        taskVO.setProjectId(2);
        taskVO.setTestTime(new Date());
        taskVO.setCreateTime(new Date());
        taskVO.setState(CONST.STATE_OPEN);
        taskVO.setDescription("我真的不喜欢写测试!!!");
        taskVO.setWorkerAmount(10);
        taskVO.setId(1);
        ResultVO<TaskVO> ans = taskService.updateTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //缺少必要的数据
        taskVO.setName("啦啦啦德玛西亚");
        taskVO.setWorkerAmount(10000);
        ans = taskService.updateTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //需求人数不能超过项目总人数
        taskVO.setProjectId(200);
        taskVO.setWorkerAmount(1);
        ans = taskService.updateTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //不存在的项目
        taskVO.setProjectId(2);
        ans = taskService.updateTask(taskVO);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testDelete(){
        Assert.assertEquals(CONST.REQUEST_FAIL,taskService.deleteTask(10000).getCode().intValue());
        Assert.assertEquals(CONST.REQUEST_SUCCESS,taskService.deleteTask(1).getCode().intValue());
    }
    @Test
    public void testJoin(){
        UserTaskVO userTaskVO = new UserTaskVO();
        userTaskVO.setUserId(3);
        userTaskVO.setTaskId(1);
        ResultVO<TaskVO> ans = taskService.joinTask(userTaskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //这里是因为用户已经加入需求
        userTaskVO.setUserId(30);
        ans = taskService.joinTask(userTaskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //不存在的用户加入需求
        userTaskVO.setUserId(3);
        userTaskVO.setTaskId(100);
        ans = taskService.joinTask(userTaskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //用户加入不存在的需求
        userTaskVO.setUserId(2);
        userTaskVO.setTaskId(1);
        ans = taskService.joinTask(userTaskVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //需求人数已满
        userTaskVO.setUserId(1);
        userTaskVO.setTaskId(2);
        ans = taskService.joinTask(userTaskVO);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
}
