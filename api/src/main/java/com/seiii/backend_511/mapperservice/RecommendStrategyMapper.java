package com.seiii.backend_511.mapperservice;

import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;

import java.util.List;

public interface RecommendStrategyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendStrategyInfo record);

    RecommendStrategyInfo selectByPrimaryKey(Integer id);
    RecommendStrategyInfo selectOnUse();
    List<RecommendStrategyInfo> selectAll();

    int updateByPrimaryKey(RecommendStrategyInfo record);
}