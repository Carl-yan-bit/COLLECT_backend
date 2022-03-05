package com.seiii.backend_511.unit.service;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.service.file.FileService;
import com.seiii.backend_511.vo.file.FileVO;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class FileServiceTest {
    @Resource
    FileService fileService;

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Test
    public void testUploadFile() throws IOException {
        FileVO fileVO=new FileVO();
        fileVO.setCarrierId(1);
        File file=new File("src/test/java/com/seiii/backend_511/UploadServiceTest.txt");
        FileInputStream fileInputStream=new FileInputStream(file);
        MultipartFile multipartFile=new MockMultipartFile("file",file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),fileInputStream);

        int tempByte;
        String resultProject="";
        fileVO.setCarrierType("project");
        fileService.uploadFile(fileVO,multipartFile);
        File projectFile=new File("file/project/project1/UploadServiceTest.txt");
        FileInputStream projectFileInputStream=new FileInputStream(projectFile);
        BufferedReader projectbf=new BufferedReader(new InputStreamReader(projectFileInputStream));
        Assert.assertEquals(true,projectFile.exists());
        while((tempByte=projectbf.read())!=-1){
            resultProject+=(char)tempByte;
        }
        Assert.assertEquals(resultProject,"this is UploadServiceTest");

        String resultReport="";
        fileVO.setCarrierType("report");
        fileService.uploadFile(fileVO,multipartFile);
        File reportFile=new File("file/report/report1/UploadServiceTest.txt");
        FileInputStream reportFileInputStream=new FileInputStream(reportFile);
        BufferedReader reportbf=new BufferedReader(new InputStreamReader(reportFileInputStream));
        Assert.assertEquals(true,reportFile.exists());
        while((tempByte=reportbf.read())!=-1){
            resultReport+=(char)tempByte;
        }
        Assert.assertEquals(resultReport,"this is UploadServiceTest");

        String resultTask="";
        fileVO.setCarrierType("task");
        fileService.uploadFile(fileVO,multipartFile);
        File taskFile=new File("file/task/task1/UploadServiceTest.txt");
        FileInputStream taskFileInputStream=new FileInputStream(taskFile);
        BufferedReader taskbf=new BufferedReader(new InputStreamReader(taskFileInputStream));
        Assert.assertEquals(true,taskFile.exists());
        while((tempByte=taskbf.read())!=-1){
            resultTask+=(char)tempByte;
        }
        Assert.assertEquals(resultTask,"this is UploadServiceTest");
    }

    @Test
    public void testDownloadFile() throws IOException {
        FileVO fileVO=new FileVO();
        fileVO.setId(2);
        fileVO.setCarrierType("project");
        MockHttpServletResponse response=new MockHttpServletResponse();
        File file  = new File("src/test/java/com/seiii/backend_511/DownloadServiceTest.txt");
        file.delete();
        FileOutputStream fout = new FileOutputStream(file);
        ByteArrayInputStream bin = new ByteArrayInputStream(response.getContentAsByteArray());
        StreamUtils.copy(bin,fout);
        fileService.downloadFile(fileVO.getCarrierType(),fileVO.getId(),response);
        fout.close();
        Assert.assertEquals(true,file.exists());
    }

    @Test
    public void testDeleteFile(){
        FileVO fileVO=new FileVO();
        fileVO.setId(1);
        fileVO.setCarrierId(1);
        fileVO.setCarrierType("report");
        fileService.deleteFile(fileVO);
        File file=new File("file/report/report/report1.txt");
        Assert.assertEquals(false,file.exists());
    }

    @Test
    public void testGetFilesByCarrierId() throws ParseException {
        Assert.assertEquals(1,fileService.getFilesByCarrierId("project",2,1,5).getSize());
    }
}
