package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.project.UserProject;
import java.util.List;

public interface UserProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserProject record);

    UserProject selectByPrimaryKey(Integer id);

    List<UserProject> selectAll();
    List<UserProject> selectByUser(Integer uid);
    List<UserProject> selectByProjects(Integer pid);
    int updateByPrimaryKey(UserProject record);
}