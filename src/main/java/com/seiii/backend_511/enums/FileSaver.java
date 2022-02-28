package com.seiii.backend_511.enums;

import com.seiii.backend_511.service.serviceimpl.fileImpl.fileUploadStrategy.FileUpload;
import com.seiii.backend_511.service.serviceimpl.fileImpl.fileUploadStrategy.ProjectFileUploadImpl;
import com.seiii.backend_511.service.serviceimpl.fileImpl.fileUploadStrategy.ReportFileUploadImpl;
import com.seiii.backend_511.service.serviceimpl.fileImpl.fileUploadStrategy.TaskFileUploadImpl;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Getter
public enum FileSaver {
    PROJECT(FileSaver.ApplicationContextInjector.applicationContext.getBean(ProjectFileUploadImpl.class)),
    REPORT(FileSaver.ApplicationContextInjector.applicationContext.getBean(ReportFileUploadImpl.class)),
    TASK(FileSaver.ApplicationContextInjector.applicationContext.getBean(TaskFileUploadImpl.class));


    private FileUpload saveStrategy;

    FileSaver(FileUpload saveStrategy){
        this.saveStrategy=saveStrategy;
    }

    @Component
    public static class ApplicationContextInjector implements ApplicationContextAware {
        public static ApplicationContext applicationContext;
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }
}
