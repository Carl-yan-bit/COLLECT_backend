package com.seiii.backend_511.service.serviceimpl.recommend;

import com.seiii.backend_511.mapperservice.UserProjectMapper;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.service.recommend.RecommendStrategy;
import com.seiii.backend_511.service.recommend.RecommendStrategyFactory;
import com.seiii.backend_511.service.serviceimpl.recommend.recommendStrategyImpl.RecommendByTimes;
import com.seiii.backend_511.service.serviceimpl.recommend.recommendStrategyImpl.RecommendByUserCF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecommendStrategyFactoryImpl implements RecommendStrategyFactory {
    @Resource
    RecommendByTimes recommendByTimes;
    @Resource
    RecommendByUserCF recommendByUserCF;
    @Resource
    UserProjectMapper userProjectMapper;

    @Override
    public RecommendStrategy getRecommendStrategy(Integer uid) {
        List<Integer> neighbor = userProjectMapper.getNeighbors(uid);
        if(neighbor==null||neighbor.size()==0){
            return recommendByTimes;
            //对于没有近邻用户的冷启动
        }
        return recommendByUserCF;
    }
}
