package com.seiii.backend_511.service.recommend;

public interface RecommendStrategyFactory {
    RecommendStrategy getRecommendStrategy(Integer uid);
}
