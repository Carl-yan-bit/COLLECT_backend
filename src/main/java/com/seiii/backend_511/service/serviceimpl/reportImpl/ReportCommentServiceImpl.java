package com.seiii.backend_511.service.serviceimpl.reportImpl;

import com.seiii.backend_511.mapperservice.ReportCommentMapper;
import com.seiii.backend_511.mapperservice.UserLogMapper;
import com.seiii.backend_511.po.UserLog;
import com.seiii.backend_511.po.report.ReportComment;
import com.seiii.backend_511.service.report.ReportCommentService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.report.ReportCommentVO;
import com.seiii.backend_511.vo.report.ReportVO;
import com.seiii.backend_511.vo.user.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReportCommentServiceImpl implements ReportCommentService {
    @Resource
    ReportCommentMapper reportCommentMapper;
    @Resource
    ReportService reportService;
    @Resource
    UserService userService;
    @Resource
    UserLogMapper userLogMapper;

    @Override
    public ResultVO<List<ReportCommentVO>> getAllCommentsById(Integer report_id) {
        if(reportService.getReportByID(report_id).getCode().equals(CONST.REQUEST_FAIL)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个报告");
        }
        List<ReportCommentVO> list = new ArrayList<>();
        for(ReportComment reportComment:reportCommentMapper.selectByReport(report_id)){
            list.add(toReportCommentVO(reportComment));
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",list);
    }

    @Override
    public ResultVO<ReportCommentVO> postComment(ReportCommentVO reportCommentVO) {
        int report_id = reportCommentVO.getReportId();
        int user_id = reportCommentVO.getUserId();
        reportCommentVO.setCreateTime(new Date());
        if(userService.getUserByUid(user_id)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        UserLog userLog = new UserLog(user_id,"评论",CONST.COMMENT_POINT,new Date());
        userLogMapper.insert(userLog);
        reportCommentVO.setUserName(userService.getUserByUid(user_id).getName());
        if(reportService.getReportByID(report_id).getCode().equals(CONST.REQUEST_FAIL)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个报告");
        }
        if(reportService.getReportByID(report_id).getData().getUserId().equals(user_id)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"不能评论给自己");
        }
        if(reportCommentMapper.selectByReport(report_id).size()==0){
            //第一次评论时初始化评分
            ReportVO reportVO = reportService.getReportByID(report_id).getData();
            reportVO.setScore(0.0F);
            reportService.updateReport(reportVO);
        }
        if(reportCommentVO.getScore()>=4){
            //大于4分的评价可以给用户涨经验
            UserVO userVO = userService.getUserByUid(reportService.getReportByID(report_id).getData().getUserId());
            float temp = reportCommentVO.getScore();
            userService.getExp(userVO, (int) temp);
        }
        if(reportCommentMapper.insert(new ReportComment(reportCommentVO))==1){
            //插入，并改变报告分数
            ReportVO reportVO = reportService.getReportByID(report_id).getData();
            reportVO.setScore(reportCommentMapper.getScoreByReport(report_id));
            reportService.updateReport(reportVO);
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",reportCommentVO);
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"失败，未知错误");
    }


    private ReportCommentVO toReportCommentVO(ReportComment reportComment){
        ReportCommentVO reportCommentVO = new ReportCommentVO(reportComment);
        reportCommentVO.setUserName(userService.getUserByUid(reportComment.getUserId()).getName());
        reportCommentVO.setUserLevel(userService.getUserByUid(reportComment.getUserId()).getLevel());
        return reportCommentVO;
    }

    private List<ReportCommentVO> toReportCommentVO(List<ReportComment> reportComments){
        List<ReportCommentVO> result=new ArrayList<>();
        for(int i=0;i<reportComments.size();i++){
            result.add(toReportCommentVO(reportComments.get(i)));
        }
        return result;
    }

    @Override
    public ResultVO<List<ReportCommentVO>> getCommentsByUID(Integer uid) {
        if(uid==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"uid为空",new LinkedList<>());
        }
        List<ReportComment> reportComments=reportCommentMapper.selectByUID(uid);
        if(reportComments==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"uid不合规",new LinkedList<>());
        }
        List<ReportCommentVO> reportCommentVOS=toReportCommentVO(reportComments);
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功查询",reportCommentVOS);
    }
}
