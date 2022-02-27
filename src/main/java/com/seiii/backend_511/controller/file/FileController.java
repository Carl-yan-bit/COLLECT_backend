package com.seiii.backend_511.controller.file;


import com.seiii.backend_511.service.file.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;
}
