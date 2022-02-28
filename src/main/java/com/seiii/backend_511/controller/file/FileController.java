package com.seiii.backend_511.controller.file;


import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.service.file.FileService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.file.FileVO;
import com.seiii.backend_511.vo.file.ProjectFileVO;
import com.seiii.backend_511.vo.file.ReportFileVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;
    @PostMapping("/upload")
    public ResultVO<FileVO> uploadFile(@RequestParam String type,@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(type,file);
    }


    @GetMapping("/download")
    public ResultVO<FileVO> download(@RequestParam String name, HttpServletResponse response) {
        return fileService.downloadFile(name, response);
    }

    @GetMapping("/{carrierType}/{carrierId}/{page}")
    public PageInfo<FileVO> getFilesByCarrierId(@PathVariable String carrierType,@PathVariable Integer carrierId,@PathVariable Integer page){
        return fileService.getFilesByCarrierId(carrierType,carrierId,page, CONST.FILE_PAGE_sIZE);
    }

}
