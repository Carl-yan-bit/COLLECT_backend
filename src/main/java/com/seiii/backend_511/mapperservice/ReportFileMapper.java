package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.file.ReportFile;
import java.util.List;

public interface ReportFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportFile record);

    ReportFile selectByPrimaryKey(Integer id);

    List<ReportFile> selectAll();

    int updateByPrimaryKey(ReportFile record);
}