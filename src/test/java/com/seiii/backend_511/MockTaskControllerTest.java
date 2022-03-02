package com.seiii.backend_511;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.backend_511.controller.user.UserController;
import com.seiii.backend_511.po.task.UserTask;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.task.UserTaskVO;
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
public class MockTaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTaskCreate() throws Exception {
        TaskVO taskVO = new TaskVO();
        taskVO.setProjectId(1);
        taskVO.setTestTime(new Date());
        taskVO.setDescription("测试不会写，招募测试人员");
        taskVO.setName("金陵大学黑马程序员学院");
        taskVO.setWorkerAmount(1);
        taskVO.setState(CONST.STATE_OPEN);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(taskVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/task/create").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testTaskUpdate() throws Exception {
        TaskVO taskVO = new TaskVO();
        taskVO.setId(1);
        taskVO.setProjectId(1);
        taskVO.setTestTime(new Date());
        taskVO.setDescription("测试不会写，招募测试人员");
        taskVO.setName("金陵大学黑马程序员学院");
        taskVO.setWorkerAmount(1);
        taskVO.setState(CONST.STATE_OPEN);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(taskVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/task/update").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testJoinTask() throws Exception{
        UserTaskVO userTaskVO = new UserTaskVO();
        userTaskVO.setTaskId(1);
        userTaskVO.setUserId(1);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userTaskVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/task/join").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testQuitTask() throws Exception{
        UserTaskVO userTaskVO = new UserTaskVO();
        userTaskVO.setTaskId(1);
        userTaskVO.setUserId(3);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userTaskVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/task/quit").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testdeleteTask() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/task/delete").param("taskId","1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetTasksByProject() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/task/find/1").param("project_id","1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetActiveTasksByProject() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/task/find/active/1").param("project_id","1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetHistoryTasksByUID() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/task/find/user/history/1").param("uid","3")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetNowTasksByUID() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/task/find/user/now/1").param("uid","3")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
}
