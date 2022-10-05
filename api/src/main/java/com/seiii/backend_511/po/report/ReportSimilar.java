package com.seiii.backend_511.po.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.vo.report.ReportVO;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class ReportSimilar {
    private Report report;

    private Double similarity;
    public ReportSimilar(){}


    public ReportSimilar(@NonNull Report report, Double sim) {
        this.report=report;
        this.similarity=sim;
    }
}
