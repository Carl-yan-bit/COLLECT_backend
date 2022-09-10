package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.ProjectMapper;
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
public class ProjectMapperTest {
    @Resource
    ProjectMapper projectMapper;

    @Test
    public void testSelectByUID(){
        Assert.assertEquals(5,projectMapper.selectByUserId(2).size());
    }
}
