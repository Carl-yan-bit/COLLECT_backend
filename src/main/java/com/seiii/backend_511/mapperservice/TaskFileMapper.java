package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.file.ReportFile;
import com.seiii.backend_511.po.file.TaskFile;
import java.util.List;

public interface TaskFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskFile record);

    TaskFile selectByPrimaryKey(Integer id);

    List<TaskFile> selectAll();

    int updateByPrimaryKey(TaskFile record);

    List<TaskFile> selectByTaskId(Integer taskId);
}