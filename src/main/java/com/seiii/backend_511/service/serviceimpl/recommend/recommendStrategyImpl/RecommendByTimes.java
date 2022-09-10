package com.seiii.backend_511.service.serviceimpl.recommend.recommendStrategyImpl;

import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.recommend.RecommendStrategy;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecommendByTimes implements RecommendStrategy {
    @Resource
    ProjectService projectService;

    @Override
    public List<Project> getRecommend(Integer uid, RecommendStrategyInfo recommendStrategyInfo) {
        return projectService.selectAllByClickOrder(recommendStrategyInfo.getNum(),uid);
    }
}
