package com.seiii.backend_511.service.report;

import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportCommentVO;

import java.util.List;

public interface ReportCommentService {
    ResultVO<List<ReportCommentVO>> getAllCommentsById(Integer report_id);
    ResultVO<ReportCommentVO> postComment(ReportCommentVO reportCommentVO);
    ResultVO<List<ReportCommentVO>> getCommentsByUID(Integer uid);
}
