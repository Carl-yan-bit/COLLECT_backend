package com.seiii.backend_511.service.serviceimpl.reportImpl;

import com.seiii.backend_511.mapperservice.ReportMapper;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    UserService userService;
    @Resource
    TaskService taskService;
    @Resource
    ReportMapper reportMapper;
    @Override
    public ResultVO<ReportVO> createReport(ReportVO reportVO) {
        return null;
    }
}
