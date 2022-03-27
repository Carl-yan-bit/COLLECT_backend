package com.seiii.backend_511.service.report;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.po.report.Report;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportTreeVO;
import com.seiii.backend_511.vo.report.ReportVO;
import javafx.util.Pair;


import java.util.List;

import java.util.List;

public interface ReportService {
    ResultVO<ReportVO> createReport(ReportVO reportVO);
    ResultVO<ReportVO> updateReport(ReportVO reportVO);
    PageInfo<ReportVO> getReportsByTask(Integer task_id,Integer currpage);
    ResultVO<List<ReportVO>> getReportByTaskAndUID(Integer task_id, Integer uid);
    ResultVO<ReportVO> getReportByID(Integer id);
    ResultVO<ReportTreeVO> getReportTreeById(Integer integer);
    ResultVO<List<Pair<ReportVO,Double>>> getSimilarReport(ReportVO report);
}
