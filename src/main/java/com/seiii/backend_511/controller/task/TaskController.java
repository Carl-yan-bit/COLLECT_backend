package com.seiii.backend_511.controller.task;

import com.seiii.backend_511.service.task.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskService taskService;
}
