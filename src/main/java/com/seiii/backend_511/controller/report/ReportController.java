package com.seiii.backend_511.controller.report;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private ReportService reportService;
    @PostMapping("/create")
    public ResultVO<ReportVO> createReport(@RequestBody ReportVO reportVO){
        return reportService.createReport(reportVO);
    }
    @PostMapping("/update")
    public ResultVO<ReportVO> updateReport(@RequestBody ReportVO reportVO){
        return reportService.updateReport(reportVO);
    }
    @GetMapping("/find/task/{pid}")
    public PageInfo<ReportVO> getReportsByTask(@RequestParam Integer task_id,@PathVariable Integer pid){
        return reportService.getReportsByTask(task_id,pid);
    }
    @GetMapping("/find")
    public ResultVO<ReportVO> getReportByTaskAndUID(@RequestParam Integer task_id,@RequestParam Integer uid){
        return reportService.getReportByTaskAndUID(task_id,uid);
    }
}
