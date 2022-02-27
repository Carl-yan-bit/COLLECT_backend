package com.seiii.backend_511.controller.project;

import com.seiii.backend_511.po.project.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private Project project;
}
