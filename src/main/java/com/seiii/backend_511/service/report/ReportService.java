package com.seiii.backend_511.service.report;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportTreeVO;
import com.seiii.backend_511.vo.report.ReportVO;

public interface ReportService {
    ResultVO<ReportVO> createReport(ReportVO reportVO);
    ResultVO<ReportVO> updateReport(ReportVO reportVO);
    PageInfo<ReportVO> getReportsByTask(Integer task_id,Integer currpage);
    ResultVO<ReportVO> getReportByTaskAndUID(Integer task_id,Integer uid);
    ResultVO<ReportVO> getReportByID(Integer id);
    ResultVO<ReportTreeVO> getReportTreeById(Integer integer);
}
