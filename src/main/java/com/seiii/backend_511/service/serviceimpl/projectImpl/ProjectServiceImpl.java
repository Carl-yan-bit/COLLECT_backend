package com.seiii.backend_511.service.serviceimpl.projectImpl;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ProjectMapper;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.service.file.FileService;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private FileService fileService;
    @Resource
    private TaskService taskService;
    @Resource
    private ReportService reportService;
    @Resource
    private ProjectMapper projectMapper;
    @Override
    public ResultVO<ProjectVO> createProject(ProjectVO projectVO) {
        //TODO: 尚未实现文件上传，考虑在此处调用FileService中的Upload
        if(StringUtils.hasText(projectVO.getName())&&StringUtils.hasText(projectVO.getDescription())&&StringUtils.hasText(projectVO.getType())&&StringUtils.hasText(projectVO.getTestTime().toString())){
            Project project = new Project(projectVO);
            project.setCreateTime(new Date());
            projectMapper.insert(project);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"创建成功",new ProjectVO(project));
        }
        else {
            return new ResultVO<>(CONST.REQUEST_FAIL,"项目定义不完全!");
        }
    }

    @Override
    public PageInfo<ProjectVO> getProjectsByUserId(Integer uid,Integer currPage) {

        return null;
    }

}
