package com.seiii.backend_511.service.report;

import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportVO;

public interface ReportService {
    ResultVO<ReportVO> createReport(ReportVO reportVO);
}
