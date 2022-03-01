package com.seiii.backend_511.service.serviceimpl.fileImpl.fileUploadStrategy;

import com.seiii.backend_511.util.FileHelper;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.file.FileVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Component
public class ProjectFileUploadImpl implements FileUpload {

    @Value("${web.project-file-upload-path}")
    private String path;
    @Override
    public ResultVO<String> save(FileVO fileVO,MultipartFile file) throws IOException {
        ResultVO result= FileHelper.save(path+fileVO.getCarrierType()+fileVO.getCarrierId(),file);
        return result;
    }

    @Override
    public void download(FileVO fileVO, HttpServletResponse response) {
        FileHelper.download(fileVO.getResourceDir(),fileVO.getName()+"."+fileVO.getType(),response);
    }
}
