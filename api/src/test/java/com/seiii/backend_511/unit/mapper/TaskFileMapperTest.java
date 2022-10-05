package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.TaskFileMapper;
import com.seiii.backend_511.po.file.TaskFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class TaskFileMapperTest {
    @Resource
    TaskFileMapper taskFileMapper;

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testDeleteByPrimaryKey(){
        Assert.assertEquals(1,taskFileMapper.deleteByPrimaryKey(1));
    }

    @Test
    public void testInsert(){
        TaskFile taskFile=new TaskFile();
        taskFile.setName("testInsert");
        taskFile.setCreateTime(new Date());
        taskFile.setTaskId(1);
        taskFile.setType("txt");
        taskFile.setResourceDir("file/task/task1/testInsert.txt");

        Assert.assertEquals(1,taskFileMapper.insert(taskFile));
    }

    @Test
    public void testUpdateByPrimaryKey(){
        TaskFile taskFile=new TaskFile();
        taskFile.setId(1);
        taskFile.setName("testUpdate");
        taskFile.setCreateTime(new Date());
        taskFile.setTaskId(1);
        taskFile.setType("txt");
        taskFile.setResourceDir("file/task/task1/testUpdate.txt");

        Assert.assertEquals(1,taskFileMapper.updateByPrimaryKey(taskFile));
    }

    @Test
    public void testSelectByPrimaryKey() throws ParseException {
        TaskFile taskFile=new TaskFile();
        taskFile.setId(1);
        taskFile.setName("task1");
        taskFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        taskFile.setTaskId(1);
        taskFile.setType("txt");
        taskFile.setResourceDir("file/task/task1/task1.txt");

        Assert.assertEquals(taskFile,taskFileMapper.selectByPrimaryKey(1));
    }

    @Test
    public void testSelectAll() throws ParseException {
        List<TaskFile> taskFiles=new LinkedList<>();
        TaskFile taskFile1=new TaskFile();
        taskFile1.setId(1);
        taskFile1.setName("task1");
        taskFile1.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        taskFile1.setTaskId(1);
        taskFile1.setType("txt");
        taskFile1.setResourceDir("file/task/task1/task1.txt");

        TaskFile taskFile2=new TaskFile();
        taskFile2.setId(2);
        taskFile2.setName("task2");
        taskFile2.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        taskFile2.setTaskId(2);
        taskFile2.setType("txt");
        taskFile2.setResourceDir("file/task/task2/task2.txt");
        taskFiles.add(taskFile1);
        taskFiles.add(taskFile2);

        Assert.assertEquals(taskFiles,taskFileMapper.selectAll());
    }

    @Test
    public void testSelectByTaskId() throws ParseException {
        List<TaskFile> taskFiles=new LinkedList<>();
        TaskFile taskFile=new TaskFile();
        taskFile.setId(1);
        taskFile.setName("task1");
        taskFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        taskFile.setTaskId(1);
        taskFile.setType("txt");
        taskFile.setResourceDir("file/task/task1/task1.txt");
        taskFiles.add(taskFile);

        Assert.assertEquals(taskFiles,taskFileMapper.selectByTaskId(1));
    }

    @Test
    public void testSelectByDir() throws ParseException {
        TaskFile taskFile=new TaskFile();
        taskFile.setId(2);
        taskFile.setName("task2");
        taskFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        taskFile.setTaskId(2);
        taskFile.setType("txt");
        taskFile.setResourceDir("file/task/task2/task2.txt");

        Assert.assertEquals(taskFile,taskFileMapper.selectByDir("file/task/task2/task2.txt"));
    }
}
