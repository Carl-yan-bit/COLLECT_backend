package com.seiii.backend_511.unit.mapper;

import com.seiii.backend_511.mapperservice.ReportFileMapper;
import com.seiii.backend_511.po.file.ReportFile;
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
public class ReportFileMapperTest {
    @Resource
    ReportFileMapper reportFileMapper;

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testDeleteByPrimaryKey(){
        Assert.assertEquals(1,reportFileMapper.deleteByPrimaryKey(1));
    }

    @Test
    public void testInsert(){
        ReportFile reportFile=new ReportFile();
        reportFile.setName("testInsert");
        reportFile.setCreateTime(new Date());
        reportFile.setReportId(1);
        reportFile.setType("txt");
        reportFile.setResourceDir("file/report/report1/testInsert.txt");

        Assert.assertEquals(1,reportFileMapper.insert(reportFile));
    }

    @Test
    public void testUpdateByPrimaryKey(){
        ReportFile reportFile=new ReportFile();
        reportFile.setId(1);
        reportFile.setName("testUpdate");
        reportFile.setCreateTime(new Date());
        reportFile.setReportId(1);
        reportFile.setType("txt");
        reportFile.setResourceDir("file/report/report1/testUpdate.txt");

        Assert.assertEquals(1,reportFileMapper.updateByPrimaryKey(reportFile));
    }

    @Test
    public void testSelectByPrimaryKey() throws ParseException {
        ReportFile reportFile=new ReportFile();
        reportFile.setId(1);
        reportFile.setName("report1");
        reportFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        reportFile.setReportId(1);
        reportFile.setType("txt");
        reportFile.setResourceDir("file/report/report1/report1.txt");

        Assert.assertEquals(reportFile,reportFileMapper.selectByPrimaryKey(1));
    }

    @Test
    public void testSelectAll() throws ParseException {
        List<ReportFile> reportFiles=new LinkedList<>();
        ReportFile reportFile1=new ReportFile();
        reportFile1.setId(1);
        reportFile1.setName("report1");
        reportFile1.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        reportFile1.setReportId(1);
        reportFile1.setType("txt");
        reportFile1.setResourceDir("file/report/report1/report1.txt");

        ReportFile reportFile2=new ReportFile();
        reportFile2.setId(2);
        reportFile2.setName("report2");
        reportFile2.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        reportFile2.setReportId(2);
        reportFile2.setType("txt");
        reportFile2.setResourceDir("file/report/report2/report2.txt");
        reportFiles.add(reportFile1);
        reportFiles.add(reportFile2);

        Assert.assertEquals(reportFiles,reportFileMapper.selectAll());
    }

    @Test
    public void testSelectByReportId() throws ParseException {
        List<ReportFile> reportFiles=new LinkedList<>();
        ReportFile reportFile=new ReportFile();
        reportFile.setId(1);
        reportFile.setName("report1");
        reportFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        reportFile.setReportId(1);
        reportFile.setType("txt");
        reportFile.setResourceDir("file/report/report1/report1.txt");
        reportFiles.add(reportFile);

        Assert.assertEquals(reportFiles,reportFileMapper.selectByReportId(1));
    }

    @Test
    public void testSelectByDir() throws ParseException {
        ReportFile reportFile=new ReportFile();
        reportFile.setId(2);
        reportFile.setName("report2");
        reportFile.setCreateTime(simpleDateFormat.parse("2022-02-28 10:00:00"));
        reportFile.setReportId(2);
        reportFile.setType("txt");
        reportFile.setResourceDir("file/report/report2/report2.txt");

        Assert.assertEquals(reportFile,reportFileMapper.selectByDir("file/report/report2/report2.txt"));
    }
}
