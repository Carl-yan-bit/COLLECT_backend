package com.seiii.backend_511.service.serviceimpl.reportImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.seiii.backend_511.mapperservice.ReportMapper;
import com.seiii.backend_511.po.report.Report;
import com.seiii.backend_511.service.device.DeviceService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.PageInfoUtil;
import com.seiii.backend_511.util.SimilarityHepler;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportTreeVO;
import com.seiii.backend_511.vo.report.ReportVO;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.user.DeviceVO;
import com.seiii.backend_511.vo.user.UserVO;
import javafx.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
            if (task.getId().equals(reportVO.getTaskId())) {
                flag = true;
                break;
            }
        }
        if(!flag){
            return new ResultVO<>(CONST.REQUEST_FAIL,"用户尚未加入该需求");
        }
        List<Report> oldList = reportMapper.selectByTaskAndUser(reportVO.getTaskId(),reportVO.getUserId());
        if(oldList!=null&&reportVO.getParentReport()!=null){
            //只能提交一份根报告
//            if(reportVO.getParentReport()==null)
//                return new ResultVO<>(CONST.REQUEST_FAIL,"只能提交一份根报告");
            for(Report report:oldList){
                if(reportVO.getParentReport().equals(report.getParentReport())){
                    return new ResultVO<>(CONST.REQUEST_FAIL,"不要重复协作");
                }
                if(reportVO.getParentReport().equals(report.getId())){
                    reportVO.setId(report.getId());
                    return updateReport(reportVO);
                }
            }
        }
        if(StringUtils.hasText(reportVO.getName())&&StringUtils.hasText(reportVO.getDescription())&&StringUtils.hasText(reportVO.getDeviceId().toString())&&StringUtils.hasText(reportVO.getTestStep())){
            Report report = new Report(reportVO);
            report.setCreateTime(new Date());
            report.setState("finished");
            if(reportMapper.insert(report)==1){
                List<Report> newList = reportMapper.selectByTaskAndUser(report.getTaskId(),reportVO.getUserId());
                Report thisReport = report;
                for(Report r:newList){
                    if(oldList==null||!oldList.contains(r)){
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

    @Override
    public ResultVO<List<Pair<ReportVO,Double>>> getSimilarReport(ReportVO report) {
        if(report.getDescription()==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"报告内容为空",new LinkedList<>());
        }
        if(report.getTaskId()==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"报告所属任务id为空",new LinkedList<>());
        }
        List<Report> taskReportList=reportMapper.selectAllByTask(report.getTaskId());
        if(taskReportList==null || taskReportList.size()==0){
            return new ResultVO<>(CONST.REQUEST_FAIL,"任务下无其余报告",new LinkedList<>());
        }
        try {
            SimilarityHepler hepler=new SimilarityHepler();
            List<Pair<Report,Double>> tempRes= hepler.findSimilarity(report.getDescription(),taskReportList);
            List<Pair<ReportVO,Double>> res=new LinkedList<>();
            for(int i=0;i<tempRes.size();i++){
                Pair<Report,Double> p1=tempRes.get(i);
                Pair<ReportVO,Double> p2=new Pair<>(new ReportVO(p1.getKey()),p1.getValue());
                res.add(p2);
            }
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功查询",res);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"未知错误",new LinkedList<>());
    }

}
