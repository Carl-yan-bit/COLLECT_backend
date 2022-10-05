package com.seiii.backend_511.vo.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.backend_511.po.file.ProjectFile;
import com.seiii.backend_511.po.file.ReportFile;
import com.seiii.backend_511.po.file.TaskFile;
import com.seiii.backend_511.util.CONST;
import lombok.Data;

import java.util.Date;

@Data
public class FileVO {
    private Integer id;

    private Integer carrierId;

    private String carrierType;

    private String name;

    private String type;

    private String resourceDir;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public FileVO(ProjectFileVO projectFileVO){
        this.id=projectFileVO.getId();
        this.carrierId=projectFileVO.getProjectId();
        this.name=projectFileVO.getName();
        this.type=projectFileVO.getType();
        this.resourceDir=projectFileVO.getResourceDir();
        this.createTime=projectFileVO.getCreateTime();
        this.carrierType= CONST.FILE_TYPE_PROJECT;
    }

    public FileVO(ProjectFile projectFile){
        this.id=projectFile.getId();
        this.carrierId=projectFile.getProjectId();
        this.name=projectFile.getName();
        this.type=projectFile.getType();
        this.resourceDir=projectFile.getResourceDir();
        this.createTime=projectFile.getCreateTime();
        this.carrierType= CONST.FILE_TYPE_PROJECT;
    }

    public FileVO(ReportFileVO reportFileVO){
        this.id=reportFileVO.getId();
        this.carrierId=reportFileVO.getReportId();
        this.name=reportFileVO.getName();
        this.type=reportFileVO.getType();
        this.resourceDir=reportFileVO.getResourceDir();
        this.createTime=reportFileVO.getCreateTime();
        this.carrierType=CONST.FILE_TYPE_REPORT;
    }

    public FileVO(ReportFile reportFile){
        this.id=reportFile.getId();
        this.carrierId=reportFile.getReportId();
        this.name=reportFile.getName();
        this.type=reportFile.getType();
        this.resourceDir=reportFile.getResourceDir();
        this.createTime=reportFile.getCreateTime();
        this.carrierType=CONST.FILE_TYPE_REPORT;
    }

    public FileVO(TaskFileVO taskFileVO){
        this.id=taskFileVO.getId();
        this.carrierId=taskFileVO.getTaskId();
        this.name=taskFileVO.getName();
        this.type=taskFileVO.getType();
        this.resourceDir=taskFileVO.getResourceDir();
        this.createTime=taskFileVO.getCreateTime();
        this.carrierType=CONST.FILE_TYPE_TASK;
    }

    public FileVO(TaskFile taskFile){
        this.id=taskFile.getId();
        this.carrierId=taskFile.getTaskId();
        this.name=taskFile.getName();
        this.type=taskFile.getType();
        this.resourceDir=taskFile.getResourceDir();
        this.createTime=taskFile.getCreateTime();
        this.carrierType=CONST.FILE_TYPE_TASK;
    }

    public FileVO(Integer carrierId,String carrierType){
        this.carrierId=carrierId;
        this.carrierType=carrierType;
    }

    public FileVO(){

    }
}
