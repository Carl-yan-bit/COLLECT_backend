package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.vo.ProjectFile;
import java.util.List;

public interface ProjectFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectFile record);

    ProjectFile selectByPrimaryKey(Integer id);

    List<ProjectFile> selectAll();

    int updateByPrimaryKey(ProjectFile record);
}