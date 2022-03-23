package com.seiii.backend_511.po.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.vo.report.ReportCommentVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class ReportComment {
    private Integer id;

    private Integer reportId;

    private Float score;

    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public ReportComment(ReportCommentVO reportComment) {
        this.id = reportComment.getId();
        this.reportId = reportComment.getReportId();
        this.score = reportComment.getScore();
        this.content = reportComment.getContent();
        this.createTime = reportComment.getCreateTime();
    }
}