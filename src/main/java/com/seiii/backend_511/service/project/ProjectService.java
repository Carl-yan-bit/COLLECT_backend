package com.seiii.backend_511.service.project;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.project.UserProjectVO;
import org.springframework.stereotype.Service;



@Service
public interface ProjectService {
    ResultVO<ProjectVO> createProject(ProjectVO projectVO);
    PageInfo<ProjectVO> getProjectsByUserId(Integer uid,Integer currPage);
    ProjectVO getProjectById(Integer projectId);
    ResultVO<ProjectVO> getProjectByIdWithUid(Integer projectId,Integer uid);
    PageInfo<ProjectVO> getAllProjects(Integer pid);
    PageInfo<ProjectVO> getActiveProjects(Integer pid);
    PageInfo<ProjectVO> getJoinedProjects(Integer uid,Integer currpage);
    ResultVO<ProjectVO> joinProject(UserProjectVO userProjectVO);
    ResultVO<ProjectVO> quitProject(UserProjectVO userProjectVO);
    ResultVO<ProjectVO> updateProject(ProjectVO project);
    ResultVO<ProjectVO> deleteProject(Integer pid);
    ResultVO<Integer> getProjectNumbers(Integer pid);
}
