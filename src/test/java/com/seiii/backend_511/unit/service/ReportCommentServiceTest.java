package com.seiii.backend_511.unit.service;

import com.seiii.backend_511.mapperservice.ReportCommentMapper;
import com.seiii.backend_511.service.report.ReportCommentService;
import com.seiii.backend_511.vo.report.ReportCommentVO;
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
public class ReportCommentServiceTest {
    @Resource
    ReportCommentService reportCommentService;
    @Resource
    ReportCommentMapper reportCommentMapper;
    @Test
    public void testPost(){
        ReportCommentVO reportCommentVO = new ReportCommentVO(reportCommentMapper.selectAll().get(0));
        reportCommentVO.setId(null);
        reportCommentVO.setCreateTime(new Date());
        Assert.assertEquals(1,reportCommentService.postComment(reportCommentVO).getCode().intValue());
    }
    @Test
    public void testGetAllComments(){
        Assert.assertEquals(9,reportCommentService.getAllCommentsById(2).getData().size());
    }
}
