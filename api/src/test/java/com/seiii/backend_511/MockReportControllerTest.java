package com.seiii.backend_511;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.backend_511.controller.user.UserController;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.report.ReportVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ComponentScan()
@Transactional
@Rollback
public class MockReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReportCreate() throws Exception {
        ReportVO reportVO = new ReportVO();
        reportVO.setUserId(3);
        reportVO.setTaskId(2);
        reportVO.setDescription("测试不会写，招募测试人员");
        reportVO.setName("金陵大学黑马程序员学院");
        reportVO.setDeviceId(1);
        reportVO.setState(CONST.STATE_OPEN);
        reportVO.setTestStep("begin");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reportVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/report/create").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testReportUpdate() throws Exception {
        ReportVO reportVO = new ReportVO();
        reportVO.setId(2);
        reportVO.setUserId(3);
        reportVO.setTaskId(2);
        reportVO.setDescription("测试不会写，招募测试人员");
        reportVO.setName("金陵大学黑马程序员学院");
        reportVO.setDeviceId(1);
        reportVO.setState(CONST.STATE_OPEN);
        reportVO.setTestStep("begin");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reportVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/report/update").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetReportByTask() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/report/find/task/1").param("task_id","2")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetReportByTaskAndUID() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/report/find").param("task_id","2").param("uid","3")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetSimilarReport() throws Exception{
        ReportVO reportVO = new ReportVO();
        reportVO.setId(100);
        reportVO.setTestStep("1");
        reportVO.setUserId(1);
        reportVO.setTaskId(1);
        reportVO.setDeviceId(1);
        reportVO.setCreateTime(new Date());
        reportVO.setDescription("我这是一个报告测试1");
        reportVO.setState("finish");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reportVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/report/similar").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
}
