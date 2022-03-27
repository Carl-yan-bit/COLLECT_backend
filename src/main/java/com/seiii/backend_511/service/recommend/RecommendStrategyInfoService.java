package com.seiii.backend_511.service.recommend;

import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.recommend.RecommendStrategyInfoVO;

import java.util.List;

public interface RecommendStrategyInfoService {
    ResultVO<RecommendStrategyInfoVO> addRecommendStrategy(RecommendStrategyInfoVO recommendStrategyInfoVO);
    ResultVO<RecommendStrategyInfoVO> deleteRecommendStrategy(Integer id,Integer uid);
    ResultVO<RecommendStrategyInfoVO> updateRecommendStrategy(RecommendStrategyInfoVO recommendStrategyInfoVO);
    ResultVO<RecommendStrategyInfoVO> getRecommendStrategy(Integer id,Integer uid);
    ResultVO<RecommendStrategyInfoVO> putInUse(Integer id,Integer uid);
    ResultVO<List<RecommendStrategyInfoVO>> getAll(Integer uid);
}
