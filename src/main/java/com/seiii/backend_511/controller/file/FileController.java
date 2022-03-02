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
    public ResultVO<FileVO> uploadFile(FileVO fileVO,@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(fileVO,file);
    }


    @GetMapping("/download")
    public void download(@RequestBody FileVO fileVO, HttpServletResponse response) {
        fileService.downloadFile(fileVO, response);
    }

    @PostMapping("/delete")
    public ResultVO deleteFile(@RequestBody FileVO fileVO){
        return fileService.deleteFile(fileVO);
    }

    @GetMapping("/{carrierType}/{carrierId}/{page}")
    public PageInfo<FileVO> getFilesByCarrierId(@PathVariable String carrierType,@PathVariable Integer carrierId,@PathVariable Integer page){
        return fileService.getFilesByCarrierId(carrierType,carrierId,page, CONST.FILE_PAGE_SIZE);
    }

}
