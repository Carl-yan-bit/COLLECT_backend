package com.seiii.backend_511.vo.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReportTreeVO {
    private Integer id;
    private String label;
    private ReportVO reportVO;
    private List<ReportTreeVO> subReportList;
    public ReportTreeVO(ReportVO reportVO){
        label = reportVO.getName();
        id = reportVO.getId();
        this.reportVO = reportVO;
    }
}
