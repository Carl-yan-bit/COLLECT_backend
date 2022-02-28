package com.seiii.backend_511.service.serviceimpl.fileImpl.fileUploadStrategy;

import com.seiii.backend_511.util.FileHelper;
import com.seiii.backend_511.vo.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TaskFileUploadImpl implements FileUpload {

    @Value("${web.task-file-upload-path}")
    private String path;
    @Override
    public ResultVO<String> save(MultipartFile file) throws IOException {
        ResultVO result= FileHelper.save(path,file);
        return result;
    }

    @Override
    public ResultVO download(String name, HttpServletResponse response) {
        ResultVO result= FileHelper.download(path,name,response);
        return result;
    }
}
