package com.seiii.backend_511.service.recommend;

import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.po.project.Project;

import java.util.List;

public interface RecommendStrategy {
    List<Project> getRecommend(Integer uid, RecommendStrategyInfo recommendStrategyInfo);
}
