package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.project.Project;
import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    Project selectByPrimaryKey(Integer id);

    List<Project> selectAll();
    List<Project> selectAllByClickOrder(Integer nums,Integer uid);
    int updateByPrimaryKey(Project record);
    List<Project> selectByUserId(Integer uid);
}