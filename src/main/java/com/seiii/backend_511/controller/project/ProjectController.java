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
        //TODO
        return null;
    }

    @GetMapping("/active/{page}")
    public PageInfo<ProjectVO> getActiveProjects(){
        //TODO 获取仍需要工人的项目
        return null;
    }

    @GetMapping("/all/{page}")
    public PageInfo<ProjectVO> getAllProjects(){
        //TODO
        return null;
    }

    //TODO mapping未设置
    @GetMapping("/")
    public PageInfo<ProjectVO> getProjectsByUserId(){
        //TODO
        return null;
    }

    @GetMapping("/{projectId}")
    public ProjectVO getProjectById(@PathVariable Integer projectId){
        //TODO
        return null;
    }
}
