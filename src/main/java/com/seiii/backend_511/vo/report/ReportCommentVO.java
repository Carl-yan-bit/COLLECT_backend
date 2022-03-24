package com.seiii.backend_511.vo.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.report.ReportComment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
@Data
@NoArgsConstructor
public class ReportCommentVO {
    private Integer id;
    private Integer userId;
    private Integer reportId;
    private String userName;
    private Float score;

    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public ReportCommentVO(@NonNull ReportComment reportComment) {
        this.id = reportComment.getId();
        this.reportId = reportComment.getReportId();
        this.score = reportComment.getScore();
        this.content = reportComment.getContent();
        this.userId = reportComment.getUserId();
        this.createTime = reportComment.getCreateTime();
    }
}