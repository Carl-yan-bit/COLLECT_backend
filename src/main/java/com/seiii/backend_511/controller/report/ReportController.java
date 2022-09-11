package com.seiii.backend_511.controller.report;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.service.report.ReportCommentService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportCommentVO;
import com.seiii.backend_511.vo.report.ReportSimilarVO;
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

    /**
     * 创建report
     * POST: /report
     * @param reportVO
     * @return
     */
    @PostMapping("")
    public ResultVO<ReportVO> createReport(@RequestBody ReportVO reportVO){
        return reportService.createReport(reportVO);
    }

    /**
     * 更新report
     * PUT: /report
     * @param reportVO
     * @return
     */
    @PutMapping("")
    public ResultVO<ReportVO> updateReport(@RequestBody ReportVO reportVO){
        return reportService.updateReport(reportVO);
    }

    /**
     * 查询report
     * GET: /report/{reportId}
     * @param reportId
     * @return
     */
    @GetMapping("/{reportId}")
    public ResultVO<ReportVO> getReportByID(@PathVariable Integer reportId){
        return reportService.getReportByID(reportId);
    }

    /**
     * 查看需求下所有报告
     * GET: /report?taskId={taskId}&page={page}
     * @param taskId
     * @param page
     * @return
     */
    @GetMapping("")
    public PageInfo<ReportVO> getReportsByTask(@RequestParam Integer taskId,
                                               @RequestParam(required = false, defaultValue = "1") Integer page){
        return reportService.getReportsByTask(taskId,page);
    }

    /**
     * 查看特定用户特定需求的报告
     * @param task_id
     * @param uid
     * @return
     */
    @GetMapping("/find")
    public ResultVO<List<ReportVO>> getReportByTaskAndUID(@RequestParam Integer task_id,@RequestParam Integer uid){
        return reportService.getReportByTaskAndUID(task_id,uid);
    }

    /**
     * 获取报告树
     * GET: /report/{reportId}/reportTree
     * @param reportId
     * @return
     */
    @GetMapping("/{reportId}/reportTree")
    public ResultVO<ReportTreeVO> getReportTreeByID(@PathVariable Integer reportId){
        return reportService.getReportTreeById(reportId);
    }

    /**
     * 获取报告所有评论
     * GET: /report/{reportId}/comment
     * @param reportId
     * @return
     */
    @GetMapping("/{reportId}/comment")
    public ResultVO<List<ReportCommentVO>> getReportCommentsByReport(@PathVariable Integer reportId){
        return reportCommentService.getAllCommentsById(reportId);
    }

    /**
     * 添加报告
     * POST: /report/comment
     * @param reportCommentVO
     * @return
     */
    @PostMapping("/comment")
    public ResultVO<ReportCommentVO> postComment(@RequestBody ReportCommentVO reportCommentVO){
        return reportCommentService.postComment(reportCommentVO);
    }

    /**
     * 获取相似报告
     * POST: /report/similar
     * @param report
     * @return
     */
    @PostMapping("/similar")
    public ResultVO<List<ReportSimilarVO>> getSimilarReport(@RequestBody ReportVO report){
        return reportService.getSimilarReport(report);
    }
}
