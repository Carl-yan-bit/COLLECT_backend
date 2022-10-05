package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.task.Task;
import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    List<Task> selectAll();
    List<Task> selectByProject(Integer pid);
    int updateByPrimaryKey(Task record);
}