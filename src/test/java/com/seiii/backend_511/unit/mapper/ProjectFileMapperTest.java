package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.ProjectFileMapper;
import com.seiii.backend_511.po.file.ProjectFile;
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
public class ProjectFileMapperTest {
    @Resource
    ProjectFileMapper projectFileMapper;

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testDeleteByPrimaryKey(){
        Assert.assertEquals(1,projectFileMapper.deleteByPrimaryKey(1));
    }

    @Test
    public void testInsert(){
        ProjectFile projectFile=new ProjectFile();
        projectFile.setName("testInsert");
        projectFile.setCreateTime(new Date());
        projectFile.setProjectId(1);
        projectFile.setType("txt");
        projectFile.setResourceDir("file/project/project1/testInsert.txt");

        Assert.assertEquals(1,projectFileMapper.insert(projectFile));
    }

    @Test
    public void testUpdateByPrimaryKey(){
        ProjectFile projectFile=new ProjectFile();
        projectFile.setId(1);
        projectFile.setName("testUpdate");
        projectFile.setCreateTime(new Date());
        projectFile.setProjectId(1);
        projectFile.setType("txt");
        projectFile.setResourceDir("file/project/project1/testUpdate.txt");

        Assert.assertEquals(1,projectFileMapper.updateByPrimaryKey(projectFile));
    }

    @Test
    public void testSelectByPrimaryKey() throws ParseException {
        ProjectFile projectFile=new ProjectFile();
        projectFile.setId(1);
        projectFile.setName("project1");
        projectFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        projectFile.setProjectId(1);
        projectFile.setType("txt");
        projectFile.setResourceDir("file/project/project1/project1.txt");

        Assert.assertEquals(projectFile,projectFileMapper.selectByPrimaryKey(1));
    }

    @Test
    public void testSelectAll() throws ParseException {
        List<ProjectFile> projectFiles=new LinkedList<>();
        ProjectFile projectFile1=new ProjectFile();
        projectFile1.setId(1);
        projectFile1.setName("project1");
        projectFile1.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        projectFile1.setProjectId(1);
        projectFile1.setType("txt");
        projectFile1.setResourceDir("file/project/project1/project1.txt");

        ProjectFile projectFile2=new ProjectFile();
        projectFile2.setId(2);
        projectFile2.setName("project2");
        projectFile2.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        projectFile2.setProjectId(2);
        projectFile2.setType("txt");
        projectFile2.setResourceDir("file/project/project2/project2.txt");
        projectFiles.add(projectFile1);
        projectFiles.add(projectFile2);

        Assert.assertEquals(projectFiles,projectFileMapper.selectAll());
    }

    @Test
    public void testSelectByProjectId() throws ParseException {
        List<ProjectFile> projectFiles=new LinkedList<>();
        ProjectFile projectFile=new ProjectFile();
        projectFile.setId(1);
        projectFile.setName("project1");
        projectFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        projectFile.setProjectId(1);
        projectFile.setType("txt");
        projectFile.setResourceDir("file/project/project1/project1.txt");
        projectFiles.add(projectFile);

        Assert.assertEquals(projectFiles,projectFileMapper.selectByProjectId(1));
    }

    @Test
    public void testSelectByDir() throws ParseException {
        ProjectFile projectFile=new ProjectFile();
        projectFile.setId(2);
        projectFile.setName("project2");
        projectFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        projectFile.setProjectId(2);
        projectFile.setType("txt");
        projectFile.setResourceDir("file/project/project2/project2.txt");

        Assert.assertEquals(projectFile,projectFileMapper.selectByDir("file/project/project2/project2.txt"));
    }
}
