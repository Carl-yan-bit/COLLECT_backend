package com.seiii.backend_511.service.project;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import org.springframework.stereotype.Service;


@Service
public interface ProjectService {
    ResultVO<ProjectVO> createProject(ProjectVO projectVO);
    PageInfo<ProjectVO> getProjectsByUserId(Integer uid,Integer currPage);
    ProjectVO getProjectById(Integer projectId);
    PageInfo<ProjectVO> getAllProjects(Integer pid);
}
