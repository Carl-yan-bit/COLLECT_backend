package com.seiii.backend_511.controller.project;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.project.UserProjectVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectservice;

    @PostMapping("/create")
    public ResultVO<ProjectVO> createProject(@RequestBody ProjectVO project){
        return projectservice.createProject(project);
    }
    @PostMapping("/update")
    public ResultVO<ProjectVO> updateProject(@RequestBody ProjectVO project){
        return projectservice.updateProject(project);
    }
    @GetMapping("/delete")
    public ResultVO<ProjectVO> deleteProject(@RequestParam Integer pid){
        return projectservice.deleteProject(pid);
    }

    @GetMapping("/active/{pid}")
    public PageInfo<ProjectVO> getActiveProjects(@PathVariable Integer pid){
        return projectservice.getActiveProjects(pid);
    }

    @GetMapping("/all/{pid}")
    public PageInfo<ProjectVO> getAllProjects(@PathVariable Integer pid){
        return projectservice.getAllProjects(pid);
    }

    @GetMapping("/find/user/{pid}")
    public PageInfo<ProjectVO> getProjectsByUserId(@RequestParam Integer uid,@PathVariable Integer pid){
        return projectservice.getProjectsByUserId(uid,pid);
    }

    @GetMapping("/find/project")
    public ProjectVO getProjectById(@RequestParam Integer projectId){
        return projectservice.getProjectById(projectId);
    }
    @PostMapping("/join")
    public ResultVO<ProjectVO> joinProject(@RequestBody UserProjectVO userProjectVO){
        return projectservice.joinProject(userProjectVO);
    }
    @PostMapping("/quit")
    public ResultVO<ProjectVO> quitProject(@RequestBody UserProjectVO userProjectVO){
        return projectservice.quitProject(userProjectVO);
    }
}
