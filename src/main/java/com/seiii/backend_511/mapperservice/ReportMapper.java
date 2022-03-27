package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.report.Report;
import java.util.List;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Report record);

    Report selectByPrimaryKey(Integer id);

    List<Report> selectAll();
    List<Report> selectByTask(Integer taskID);
    List<Report> selectAllByTask(Integer taskID);
    List<Report> selectByParentReport(Integer parentReport);
    List<Report> selectByTaskAndUser(Integer taskID,Integer UID);
    int updateByPrimaryKey(Report record);
}