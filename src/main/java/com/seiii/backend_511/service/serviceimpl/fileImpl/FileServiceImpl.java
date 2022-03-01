package com.seiii.backend_511.service.serviceimpl.fileImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.enums.FileSaver;
import com.seiii.backend_511.mapperservice.ProjectFileMapper;
import com.seiii.backend_511.mapperservice.ReportFileMapper;
import com.seiii.backend_511.mapperservice.TaskFileMapper;
import com.seiii.backend_511.po.file.ProjectFile;
import com.seiii.backend_511.po.file.ReportFile;
import com.seiii.backend_511.po.file.TaskFile;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.service.file.FileService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.PageInfoUtil;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.file.FileVO;
import com.seiii.backend_511.vo.file.ProjectFileVO;
import com.seiii.backend_511.vo.file.ReportFileVO;
import com.seiii.backend_511.vo.file.TaskFileVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private ProjectFileMapper projectFileMapper;
    @Resource
    private ReportFileMapper reportFileMapper;
    @Resource
    private TaskFileMapper taskFileMapper;
    @Override
    public ResultVO<FileVO> uploadFile(FileVO fileVO, MultipartFile file) {
        ResultVO<String> saveResult=null;
        try {
            switch (fileVO.getCarrierType()){
                case CONST.FILE_TYPE_PROJECT:
                    saveResult=FileSaver.PROJECT.getSaveStrategy().save(fileVO,file);
                    if(saveResult!=null){
                        String dir=saveResult.getData();
                            if(StringUtils.hasText(dir)){
                                ResultVO<FileVO> result=setUploadResponse(dir,file,fileVO);
                                if(projectFileMapper.selectByDir(dir)!=null){
                                    result.setMsg("文件更新成功");
                                }else {
                                    ProjectFile projectFile=new ProjectFile(fileVO);
                                    projectFileMapper.insert(projectFile);
                                }
                                return result;
                            }
                    }
                    break;
                case CONST.FILE_TYPE_REPORT:
                    saveResult=FileSaver.REPORT.getSaveStrategy().save(fileVO,file);
                    if(saveResult!=null){
                        String dir=saveResult.getData();
                        if(StringUtils.hasText(dir)){
                            ResultVO<FileVO> result=setUploadResponse(dir,file,fileVO);
                            if(reportFileMapper.selectByDir(dir)!=null){
                                result.setMsg("文件更新成功");
                            }else {
                                ReportFile reportFile=new ReportFile(fileVO);
                                reportFileMapper.insert(reportFile);
                            }
                            return result;
                        }
                    }
                    break;
                case CONST.FILE_TYPE_TASK:
                    saveResult=FileSaver.TASK.getSaveStrategy().save(fileVO,file);
                    if(saveResult!=null){
                        String dir=saveResult.getData();
                        if(StringUtils.hasText(dir)){
                            ResultVO<FileVO> result=setUploadResponse(dir,file,fileVO);
                            if(taskFileMapper.selectByDir(dir)!=null){
                                result.setMsg("文件更新成功");
                            }else {
                                TaskFile taskFile=new TaskFile(fileVO);
                                taskFileMapper.insert(taskFile);
                            }
                            return result;
                        }
                    }
                    break;
                default:
                    return new ResultVO<>(CONST.REQUEST_FAIL,"文件所属类型错误");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResultVO<>(CONST.REQUEST_FAIL, "服务器错误，请联系网站管理员。");
    }

    @Override
    public ResultVO<FileVO> downloadFile(FileVO fileVO, HttpServletResponse response) {
        ResultVO downloadResult=null;
        if(fileVO.getCarrierType()!=null){
            FileVO mapperResult=null;
            switch (fileVO.getCarrierType()){
                case CONST.FILE_TYPE_PROJECT:
                    ProjectFile projectFile=projectFileMapper.selectByPrimaryKey(fileVO.getId());
                    mapperResult=new FileVO(new ProjectFileVO(projectFile));
                    downloadResult=FileSaver.PROJECT.getSaveStrategy().download(mapperResult,response);
                    break;
                case CONST.FILE_TYPE_REPORT:
                    ReportFile reportFile=reportFileMapper.selectByPrimaryKey(fileVO.getId());
                    mapperResult=new FileVO(new ReportFileVO(reportFile));
                    downloadResult=FileSaver.REPORT.getSaveStrategy().download(mapperResult,response);
                    break;
                case CONST.FILE_TYPE_TASK:
                    TaskFile taskFile=taskFileMapper.selectByPrimaryKey(fileVO.getId());
                    mapperResult=new FileVO(new TaskFileVO(taskFile));
                    downloadResult=FileSaver.TASK.getSaveStrategy().download(mapperResult,response);
                    break;
            }
        }
        return downloadResult;
    }

    @Override
    public PageInfo<FileVO> getFilesByCarrierId(String carrierType, Integer carrierId, Integer page, Integer pageSize) {
        if(page==null||page<1)page=1;
        PageHelper.startPage(page,pageSize);
        PageInfo<FileVO> result = null;
        switch (carrierType){
            case CONST.FILE_TYPE_PROJECT:
                PageInfo<ProjectFile> projectFilePageInfo=new PageInfo<>(projectFileMapper.selectByProjectId(carrierId));
                result= PageInfoUtil.convert(projectFilePageInfo,FileVO.class);
                break;
            case CONST.FILE_TYPE_REPORT:
                PageInfo<ReportFile> reportFilePageInfo=new PageInfo<>(reportFileMapper.selectByReportId(carrierId));
                result=PageInfoUtil.convert(reportFilePageInfo,FileVO.class);
                break;
            case CONST.FILE_TYPE_TASK:
                PageInfo<TaskFile> taskFilePageInfo=new PageInfo<>(taskFileMapper.selectByTaskId(carrierId));
                result=PageInfoUtil.convert(taskFilePageInfo,FileVO.class);
                break;
        }
        return result;
    }

    public ResultVO<FileVO> setUploadResponse(String dir,MultipartFile file,FileVO fileVO){
        String originalName = file.getOriginalFilename();
        fileVO.setResourceDir(dir);
        fileVO.setCreateTime(new Date());
        fileVO.setName(originalName.substring(0,originalName.lastIndexOf(".")));
        fileVO.setType(originalName.substring(originalName.lastIndexOf(".")+1));
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"文件上传成功",fileVO);
    }

}
