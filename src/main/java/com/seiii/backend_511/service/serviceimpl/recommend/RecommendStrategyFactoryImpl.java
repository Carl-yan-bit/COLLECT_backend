package com.seiii.backend_511.service.serviceimpl.recommend;

import com.seiii.backend_511.mapperservice.ProjectMapper;
import com.seiii.backend_511.mapperservice.UserMapper;
import com.seiii.backend_511.mapperservice.UserProjectMapper;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.recommend.RecommendStrategy;
import com.seiii.backend_511.service.recommend.RecommendStrategyFactory;
import com.seiii.backend_511.service.serviceimpl.recommend.recommendStrategyImpl.RecommendByItemCF;
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
    RecommendByItemCF recommendByItemCF;

    @Resource
    UserProjectMapper userProjectMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ProjectService projectService;

    @Override
    public RecommendStrategy getRecommendStrategy(Integer uid) {
        if(userMapper.selectUserWithProject().size()<20){
            if(projectService.getAllJoinedProjects(uid)==null||projectService.getAllJoinedProjects(uid).size()==0){
                return recommendByTimes;
            }
            //对于有效用户不够多时候的冷启动
            return recommendByItemCF;
        }
        List<Integer> neighbor = userProjectMapper.getNeighbors(uid);
        if(neighbor==null||neighbor.size()==0){
            return recommendByTimes;
            //对于没有近邻用户的冷启动
        }
        return recommendByUserCF;
    }
}
