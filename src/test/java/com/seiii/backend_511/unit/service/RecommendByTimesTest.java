package com.seiii.backend_511.unit.service;

import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.service.recommend.RecommendStrategy;
import com.seiii.backend_511.service.recommend.RecommendStrategyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendByTimesTest {
    @Resource
    RecommendStrategyFactory recommendStrategyFactory;

    @Test
    public void testGetRecommend(){
        RecommendStrategy strategy = recommendStrategyFactory.getRecommendStrategy();
        RecommendStrategyInfo recommendStrategyInfo = new RecommendStrategyInfo();
        recommendStrategyInfo.setNum(3);
        List<Project> projectVOS = strategy.getRecommend(1,recommendStrategyInfo);
    }
}
