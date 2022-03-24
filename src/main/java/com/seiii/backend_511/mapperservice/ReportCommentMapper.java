package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.report.ReportComment;
import java.util.List;

public interface ReportCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportComment record);
    float getScoreByReport(Integer rid);
    ReportComment selectByPrimaryKey(Integer id);

    List<ReportComment> selectAll();
    List<ReportComment> selectByReport(Integer rid);
    int updateByPrimaryKey(ReportComment record);
}