package com.seiii.backend_511.controller.report;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.po.report.ReportComment;
import com.seiii.backend_511.service.report.ReportCommentService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportCommentVO;
import com.seiii.backend_511.vo.report.ReportTreeVO;
import com.seiii.backend_511.vo.report.ReportVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private ReportService reportService;
    @Resource
    private ReportCommentService reportCommentService;

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
    public ResultVO<List<ReportVO>> getReportByTaskAndUID(@RequestParam Integer task_id,@RequestParam Integer uid){
        return reportService.getReportByTaskAndUID(task_id,uid);
    }
    @GetMapping("/find/id")
    public ResultVO<ReportVO> getReportByID(@RequestParam Integer id){
        return reportService.getReportByID(id);
    }
    @GetMapping("/find/tree")
    public ResultVO<ReportTreeVO> getReportTreeByID(@RequestParam Integer id){
        return reportService.getReportTreeById(id);
    }
    @GetMapping("/comments")
    public ResultVO<List<ReportCommentVO>> getReportCommentsByReport(@RequestParam Integer id){
        return reportCommentService.getAllCommentsById(id);
    }
    @PostMapping("/comment")
    public ResultVO<ReportCommentVO> postComment(@RequestBody ReportCommentVO reportCommentVO){
        return reportCommentService.postComment(reportCommentVO);
    }
}
