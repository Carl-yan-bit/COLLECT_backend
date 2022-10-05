package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.task.UserTask;
import java.util.List;

public interface UserTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTask record);

    UserTask selectByPrimaryKey(Integer id);

    List<UserTask> selectAll();
    List<UserTask> selectByUid(Integer uid);
    List<UserTask> selectByTask(Integer taskId);
    int updateByPrimaryKey(UserTask record);
}