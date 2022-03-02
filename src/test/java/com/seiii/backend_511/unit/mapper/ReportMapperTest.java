package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.ReportMapper;
import com.seiii.backend_511.mapperservice.TaskMapper;
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
public class ReportMapperTest {
    @Resource
    ReportMapper reportMapper;

    @Test
    public void testSelectByTask(){
        Assert.assertEquals(1,reportMapper.selectByTask(2).size());
    }
    @Test
    public void testSelectByTaskAndUID(){
        Assert.assertEquals(Integer.valueOf(2),reportMapper.selectByTaskAndUser(2,3).getId());
    }
}
