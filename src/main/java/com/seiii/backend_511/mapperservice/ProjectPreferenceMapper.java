package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.project.ProjectPreference;
import java.util.List;

public interface ProjectPreferenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPreference record);

    ProjectPreference selectByPrimaryKey(Integer id);

    List<ProjectPreference> selectAll();

    int updateByPrimaryKey(ProjectPreference record);
}