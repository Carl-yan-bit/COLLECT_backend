package com.seiii.backend_511.unit.service;

import com.seiii.backend_511.service.task.TaskService;
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
public class TaskServiceTest {
    @Resource
    TaskService taskService;

    @Test
    public void testCreateTask(){

    }
}
