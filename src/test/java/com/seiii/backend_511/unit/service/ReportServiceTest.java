package com.seiii.backend_511.unit.service;


import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportSimilarVO;
import com.seiii.backend_511.vo.report.ReportTreeVO;
import com.seiii.backend_511.vo.report.ReportVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class ReportServiceTest {
    @Resource
    ReportService reportService;

    @Test
    public void testCreate(){
        ReportVO reportVO = new ReportVO();
        reportVO.setTestStep("1");
        reportVO.setUserId(1);
        reportVO.setTaskId(3);
        reportVO.setDeviceId(1);
        reportVO.setCreateTime(new Date());
        reportVO.setDescription("f**k test");
        reportVO.setState("finish");
        ResultVO<ReportVO> ans = reportService.createReport(reportVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //这里因为填写不全
        reportVO.setName("可恶的测试");
        ans = reportService.createReport(reportVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //这里已经有过报告
        reportVO.setUserId(2);
        reportVO.setTaskId(1);
        ans = reportService.createReport(reportVO);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testUpdate(){
        ReportVO reportVO = new ReportVO();
        reportVO.setId(1);
        reportVO.setTestStep("1");
        reportVO.setUserId(1);
        reportVO.setTaskId(3);
        reportVO.setDeviceId(1);
        reportVO.setCreateTime(new Date());
        reportVO.setDescription("f**k test");
        reportVO.setState("finish");
        ResultVO<ReportVO> ans = reportService.updateReport(reportVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //这里因为填写不全
        reportVO.setName("可恶的测试");
        ans = reportService.updateReport(reportVO);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testGetReportsByTask(){
        Assert.assertEquals(0,reportService.getReportsByTask(2,1).getTotal());
    }
    @Test
    public void testGetReportsByTaskAndUID(){
        Assert.assertEquals(CONST.REQUEST_SUCCESS,reportService.getReportByTaskAndUID(1,3).getCode().intValue());
        Assert.assertEquals(CONST.REQUEST_FAIL,reportService.getReportByTaskAndUID(4,3).getCode().intValue());
    }
    @Test
    public void testGetReportTreeByID(){
        ResultVO<ReportTreeVO> temp = reportService.getReportTreeById(1);
        Assert.assertEquals(1,temp.getCode().intValue());
    }
    @Test
    public void testGetSimilarReport(){
        ReportVO reportVO = new ReportVO();
        reportVO.setId(100);
        reportVO.setTestStep("1");
        reportVO.setUserId(1);
        reportVO.setTaskId(1);
        reportVO.setDeviceId(1);
        reportVO.setCreateTime(new Date());
        reportVO.setDescription("我这是一个报告测试1");
        reportVO.setState("finish");
        ResultVO<List<ReportSimilarVO>> temp = reportService.getSimilarReport(reportVO);
        System.out.println(temp);
    }
}
