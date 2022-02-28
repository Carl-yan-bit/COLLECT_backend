package com.seiii.backend_511.service.serviceimpl.fileImpl.fileUploadStrategy;


import com.seiii.backend_511.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileUpload {

    ResultVO<String> save(MultipartFile file) throws IOException;

    ResultVO download(String name, HttpServletResponse response);

}
