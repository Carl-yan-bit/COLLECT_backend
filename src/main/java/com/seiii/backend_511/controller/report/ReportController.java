package com.seiii.backend_511.controller.report;

import com.seiii.backend_511.service.report.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private ReportService reportService;

}
