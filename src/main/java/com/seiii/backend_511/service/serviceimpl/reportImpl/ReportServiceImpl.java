package com.seiii.backend_511.service.serviceimpl.reportImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ReportMapper;
import com.seiii.backend_511.po.report.Report;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.PageInfoUtil;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportVO;
import com.seiii.backend_511.vo.task.TaskVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        if(userService.getUserByUid(reportVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(taskService.getTaskByID(reportVO.gettaskId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个任务");
        }
        boolean flag = false;
        for(TaskVO task:taskService.getTasksByUser(reportVO.getUserId())){
            if(task.getId().equals(reportVO.gettaskId())){
                flag = true;
            }
        }
        if(!flag){
            return new ResultVO<>(CONST.REQUEST_FAIL,"用户尚未加入该需求");
        }
        if(reportMapper.selectByTaskAndUser(reportVO.gettaskId(),reportVO.getUserId())!=null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"请不要重复提交");
        }
        if(StringUtils.hasText(reportVO.getName())&&StringUtils.hasText(reportVO.getDescription())&&StringUtils.hasText(reportVO.getDeviceInfo())&&StringUtils.hasText(reportVO.getTestStep())){
            Report report = new Report(reportVO);
            report.setCreateTime(new Date());
            report.setState("finished");
            reportMapper.insert(report);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"已完成!您辛苦了",new ReportVO(report));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"报告还没填写完");
    }

    @Override
    public ResultVO<ReportVO> updateReport(ReportVO reportVO) {
        if(userService.getUserByUid(reportVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(taskService.getTaskByID(reportVO.gettaskId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个任务");
        }
        if(reportMapper.selectByTaskAndUser(reportVO.gettaskId(),reportVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个报告");
        }
        if(StringUtils.hasText(reportVO.getName())&&StringUtils.hasText(reportVO.getDescription())&&StringUtils.hasText(reportVO.getDeviceInfo())&&StringUtils.hasText(reportVO.getTestStep())){
            Report report = new Report(reportVO);
            report.setCreateTime(new Date());
            report.setState("finished");
            reportMapper.updateByPrimaryKey(report);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"已完成!您辛苦了",new ReportVO(report));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"报告还没填写完");
    }

    @Override
    public PageInfo<ReportVO> getReportsByTask(Integer task_id,Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;
        List<Report> reportList = reportMapper.selectByTask(task_id);
        PageHelper.startPage(currPage,CONST.PAGE_SIZE);
        return PageInfoUtil.convert(new PageInfo<>(reportList),ReportVO.class);
    }

    @Override
    public ResultVO<ReportVO> getReportByTaskAndUID(Integer task_id, Integer uid) {
        Report report = reportMapper.selectByTaskAndUser(task_id,uid);
        if(report==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个报告");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"查找成功",new ReportVO(report));
    }
}
