package com.seiii.backend_511.unit.service;

import com.seiii.backend_511.mapperservice.RecommendStrategyMapper;
import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.service.recommend.RecommendStrategyInfoService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.recommend.RecommendStrategyInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class RecommendStrategyInfoServiceTest {
    @Resource
    RecommendStrategyInfoService recommendStrategyInfoService;
    @Resource
    RecommendStrategyMapper recommendStrategyMapper;

    @Test
    public void testAdd(){
        RecommendStrategyInfoVO recommendStrategyInfoVo = new RecommendStrategyInfoVO();
        recommendStrategyInfoVo.setOnUse(0);
        recommendStrategyInfoVo.setActivity(1);
        recommendStrategyInfoVo.setDevice(1);
        recommendStrategyInfoVo.setname("test");
        recommendStrategyInfoVo.setExp(1);
        recommendStrategyInfoVo.setType(1);
        recommendStrategyInfoVo.setDifficulty(8);
        recommendStrategyInfoVo.setUid(1);
        recommendStrategyInfoVo.setNum(4);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,recommendStrategyInfoService.addRecommendStrategy(recommendStrategyInfoVo).getCode().intValue());
    }
    @Test
    public void testDelete(){
        Assert.assertEquals(CONST.REQUEST_SUCCESS,recommendStrategyInfoService.deleteRecommendStrategy(1,1).getCode().intValue());
    }
    @Test
    public void testUse(){
        int old = recommendStrategyMapper.selectOnUse().getId();
        Assert.assertEquals(CONST.REQUEST_SUCCESS,recommendStrategyInfoService.putInUse(1,1).getCode().intValue());
        Assert.assertNotEquals(old,recommendStrategyMapper.selectOnUse().getId().intValue());
    }
    @Test
    public void testGet(){
        Assert.assertEquals(CONST.REQUEST_SUCCESS,recommendStrategyInfoService.getRecommendStrategy(1,1).getCode().intValue());
    }
}
