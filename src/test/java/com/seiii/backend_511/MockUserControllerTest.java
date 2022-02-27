package com.seiii.backend_511;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.backend_511.controller.user.UserController;
import com.seiii.backend_511.vo.user.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class MockUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testRegister() throws Exception{
        UserVO userVO = new UserVO();
        userVO.setId(1);
        userVO.setName("rubisco");
        userVO.setPassword("sys20001130");
        userVO.setEmail("1245144245@qq.com");
        userVO.setPhonenumber("15009175289");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userVO);
        String responseString = mockMvc.perform( MockMvcRequestBuilders.post("/softs").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    }
}