package com.seiii.backend_511;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.backend_511.controller.file.FileController;
import com.seiii.backend_511.controller.user.UserController;
import com.seiii.backend_511.vo.file.FileVO;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
@ComponentScan()
public class MockFileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testUpload() throws Exception{
        FileVO fileVO=new FileVO();
        fileVO.setCarrierId(1);
        fileVO.setCarrierType("project");
        File file=new File("src/test/java/com/seiii/backend_511/UploadTest.txt");
        FileInputStream fileInputStream=new FileInputStream(file);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(fileVO);
        String responseString = mockMvc.perform(MockMvcRequestBuilders
                .fileUpload("/api/file/upload")
                .file(new MockMultipartFile("file",file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),fileInputStream))
                .param("carrierId","1")
                .param("carrierType","project"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();;
    }
    @Test
    public void testDownload() throws Exception{
        FileVO fileVO=new FileVO();
        fileVO.setId(2);
        fileVO.setCarrierId(1);
        fileVO.setCarrierType("project");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(fileVO);
        String responseString=mockMvc.perform(MockMvcRequestBuilders
                .get("/api/file/download")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                    @Override
                    public void handle(MvcResult mvcResult) throws Exception {
                        File file  = new File("src/test/java/com/seiii/backend_511/DownloadTest.txt");
                        file.delete();
                        FileOutputStream fout = new FileOutputStream(file);
                        ByteArrayInputStream bin = new ByteArrayInputStream(mvcResult.getResponse().getContentAsByteArray());
                        StreamUtils.copy(bin,fout);
                        fout.close();
                        System.out.println("is exist:"+file.exists());
                        //assert
                        System.out.println("file length:"+file.length());
                    }
                }).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testDelete() throws Exception{
        FileVO fileVO=new FileVO();
        fileVO.setId(1);
        fileVO.setCarrierId(1);
        fileVO.setCarrierType("project");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(fileVO);
        String responseString=mockMvc.perform(MockMvcRequestBuilders
                .post("/api/file/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print()).andReturn().getResponse().getContentAsString();
    }
    @Test
    public void testGetFilesByCarrierId() throws Exception{
        String responseString = mockMvc.perform( MockMvcRequestBuilders.get("/api/file/project/1/1")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
}
