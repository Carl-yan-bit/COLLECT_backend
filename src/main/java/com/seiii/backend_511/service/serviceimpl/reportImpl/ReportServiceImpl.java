package com.seiii.backend_511.service.serviceimpl.reportImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.ReportMapper;
import com.seiii.backend_511.po.report.Report;
import com.seiii.backend_511.service.device.DeviceService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.PageInfoUtil;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportTreeVO;
import com.seiii.backend_511.vo.report.ReportVO;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.user.DeviceVO;
import com.seiii.backend_511.vo.user.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    UserService userService;
    @Resource
    TaskService taskService;
    @Resource
    DeviceService deviceService;
    @Resource
    ReportMapper reportMapper;
    @Override
    public ResultVO<ReportVO> createReport(ReportVO reportVO) {
        reportVO.setScore(2.5F);
        if(userService.getUserByUid(reportVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(taskService.getTaskByID(reportVO.getTaskId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个任务");
        }
        boolean flag = false;
        for(TaskVO task:taskService.getTasksByUser(reportVO.getUserId())){
            if(task.getId().equals(reportVO.getTaskId())){
                flag = true;
            }
        }
        if(!flag){
            return new ResultVO<>(CONST.REQUEST_FAIL,"用户尚未加入该需求");
        }
        if(reportMapper.selectByTaskAndUser(reportVO.getTaskId(),reportVO.getUserId())!=null&&reportVO.getParentReport()==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"请不要重复提交");
        }
        if(StringUtils.hasText(reportVO.getName())&&StringUtils.hasText(reportVO.getDescription())&&StringUtils.hasText(reportVO.getDeviceId().toString())&&StringUtils.hasText(reportVO.getTestStep())){
            Report report = new Report(reportVO);
            report.setCreateTime(new Date());
            report.setState("finished");
            List<Report> oldList = reportMapper.selectByTaskAndUser(report.getTaskId(),reportVO.getUserId());
            if(reportMapper.insert(report)==1){
                List<Report> newList = reportMapper.selectByTaskAndUser(report.getTaskId(),reportVO.getUserId());
                Report thisReport = report;
                for(Report r:newList){
                    if(!oldList.contains(r)){
                        thisReport = r;
                        break;
                    }
                }
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"已完成!您辛苦了",toReportVO(thisReport));
            }

            return new ResultVO<>(CONST.REQUEST_FAIL,"提交失败");
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"报告还没填写完");
    }

    @Override
    public ResultVO<ReportVO> updateReport(ReportVO reportVO) {
        if(userService.getUserByUid(reportVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(taskService.getTaskByID(reportVO.getTaskId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个任务");
        }
        if(reportMapper.selectByPrimaryKey(reportVO.getId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个报告");
        }
        if(StringUtils.hasText(reportVO.getName())&&StringUtils.hasText(reportVO.getDescription())&&StringUtils.hasText(reportVO.getDeviceId().toString())&&StringUtils.hasText(reportVO.getTestStep())){
            Report report = new Report(reportVO);
            report.setCreateTime(new Date());
            report.setState("finished");
//            更新一个任务肯定是有id的
//            if(report.getId()==null){
//                report.setId(reportMapper.selectByTaskAndUser(reportVO.getTaskId(),reportVO.getUserId()).getId());
//            }
            if(reportMapper.updateByPrimaryKey(report)==1)
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"已完成!您辛苦了",new ReportVO(report));
            return new ResultVO<>(CONST.REQUEST_FAIL,"提交失败");
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"报告还没填写完");
    }

    @Override
    public PageInfo<ReportVO> getReportsByTask(Integer task_id,Integer currPage) {
        if(currPage==null||currPage<1) currPage = 1;
        List<Report> reportList = reportMapper.selectByTask(task_id);
        List<ReportVO> ans = new ArrayList<>();
        for(Report r:reportList){
            ans.add(toReportVO(r));
        }
        return PageInfoUtil.ListToPageInfo(ans,currPage);
    }

    @Override
    public ResultVO<ReportVO> getReportByID(Integer id) {
        Report report = reportMapper.selectByPrimaryKey(id);
        if(report==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个报告");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",toReportVO(report));
    }

    @Override
    public ResultVO<List<ReportVO>> getReportByTaskAndUID(Integer task_id, Integer uid) {
        List<Report> report = reportMapper.selectByTaskAndUser(task_id,uid);
        if(report.size()==0){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个报告");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"查找成功",toReportVO(report));
    }
    private ReportVO toReportVO(Report report){
        ReportVO reportVO = new ReportVO(report);
        DeviceVO deviceVO = deviceService.getDeviceById(report.getDeviceId());
        if(deviceVO!=null)
            reportVO.setDeviceInfo(deviceVO.getDeviceInfo());
        UserVO userVO = userService.getUserByUid(report.getUserId());
        if(userVO!=null)
            reportVO.setUserName(userVO.getName());
        return reportVO;
    }
    private List<ReportVO> toReportVO(List<Report> report){
        List<ReportVO> reportVO = new ArrayList<>();
        for(Report reportPO:report){
            reportVO.add(toReportVO(reportPO));
        }
        return reportVO;
    }

    @Override
    public ResultVO<ReportTreeVO> getReportTreeById(Integer id) {
        if(getReportByID(id).getCode().equals(CONST.REQUEST_FAIL)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个节点");
        }
        ReportVO reportVO = getReportByID(id).getData();
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",toReportTreeVO(reportVO));
    }
    private ReportTreeVO toReportTreeVO(ReportVO reportVO){
        ReportTreeVO treeVO = new ReportTreeVO(reportVO);
        List<ReportTreeVO> list = new ArrayList<>();
        List<Report> sub = reportMapper.selectByParentReport(reportVO.getId());
        if (sub.size()==0){
            return treeVO;
        }
        for(Report report:sub){
            list.add(toReportTreeVO(toReportVO(report)));
        }
        treeVO.setChildren(list);
        return treeVO;
    }

}
