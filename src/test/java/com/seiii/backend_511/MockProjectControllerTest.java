package com.seiii.backend_511;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.backend_511.controller.user.UserController;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.project.UserProjectVO;
import com.seiii.backend_511.vo.user.UserVO;
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
public class MockProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testCreateProject() throws Exception{
        Project projectVO = new Project();
        projectVO.setUserId(1);
        projectVO.setTestTime(new Date());
        projectVO.setDescription("测试不会写，招募测试人员");
        projectVO.setName("金陵大学黑马程序员学院");
        projectVO.setWorkerAmount(1);
        projectVO.setType(CONST.STATE_OPEN);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(projectVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/project/create").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testUpdateProject() throws Exception{
        Project projectVO = new Project();
        projectVO.setId(1);
        projectVO.setUserId(1);
        projectVO.setTestTime(new Date());
        projectVO.setDescription("测试不会写，招募测试人员,我们需要更多");
        projectVO.setName("金陵大学黑马程序员学院");
        projectVO.setWorkerAmount(100);
        projectVO.setType(CONST.STATE_OPEN);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(projectVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/project/update").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testdeleteProject() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/project/delete").param("pid","1"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetActiveProjects() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/project/active/1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetAllProjects() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/project/all/1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetProjectsByUserId() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/project/find/user/1").param("uid","1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetProjectById() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/project/find/project").param("projectId","1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testJoinProject() throws Exception{
        UserProjectVO userProjectVO = new UserProjectVO();
        userProjectVO.setProjectId(1);
        userProjectVO.setUserId(1);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userProjectVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/project/join").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testQuitProject() throws Exception{
        UserProjectVO userProjectVO = new UserProjectVO();
        userProjectVO.setProjectId(1);
        userProjectVO.setUserId(3);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userProjectVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/api/project/quit").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
}
