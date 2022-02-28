package com.seiii.backend_511.service.file;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.file.FileVO;
import com.seiii.backend_511.vo.file.ProjectFileVO;
import com.seiii.backend_511.vo.file.ReportFileVO;
import com.seiii.backend_511.vo.file.TaskFileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    ResultVO<FileVO> uploadFile(String type,MultipartFile file);

    ResultVO<FileVO> downloadFile(String originName, HttpServletResponse response);

    PageInfo<FileVO> getFilesByCarrierId(String carrierType,Integer carrierId,Integer page,Integer pageSize);

}
