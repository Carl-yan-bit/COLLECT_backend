package com.seiii.backend_511.service.serviceimpl.recommend;

import com.seiii.backend_511.service.recommend.RecommendStrategy;
import com.seiii.backend_511.service.recommend.RecommendStrategyFactory;
import com.seiii.backend_511.service.serviceimpl.recommend.recommendStrategyImpl.RecommendByTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecommendStrategyFactoryImpl implements RecommendStrategyFactory {
    @Resource
    RecommendByTimes recommendByTimes;


    @Override
    public RecommendStrategy getRecommendStrategy() {

        return recommendByTimes;
    }
}
