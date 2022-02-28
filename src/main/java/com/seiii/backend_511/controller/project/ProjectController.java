package com.seiii.backend_511.controller.project;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
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

    @GetMapping("/active/{page}")
    public PageInfo<ProjectVO> getActiveProjects(){
        //TODO 获取仍需要工人的项目
        return null;
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
}
