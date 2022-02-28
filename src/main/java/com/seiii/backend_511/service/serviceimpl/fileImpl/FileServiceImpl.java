package com.seiii.backend_511.service.serviceimpl.fileImpl;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ProjectFileMapper;
import com.seiii.backend_511.mapperservice.ReportFileMapper;
import com.seiii.backend_511.mapperservice.TaskFileMapper;
import com.seiii.backend_511.service.file.FileService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.file.FileVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Service
public class FileServiceImpl implements FileService {


    @Resource
    private ProjectFileMapper projectFileMapper;
    @Resource
    private ReportFileMapper reportFileMapper;
    @Resource
    private TaskFileMapper taskFileMapper;
    @Override
    public ResultVO<FileVO> uploadFile(String type, MultipartFile file) {
        return null;
    }

    @Override
    public ResultVO<FileVO> downloadFile(String originName, HttpServletResponse response) {
        return null;
    }

    @Override
    public PageInfo<FileVO> getFilesByCarrierId(String carrierType, Integer carrierId, Integer page, Integer pageSize) {
        return null;
    }
}
