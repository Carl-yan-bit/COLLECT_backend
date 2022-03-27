package com.seiii.backend_511.vo.recommend;

import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RecommendStrategyInfoVO {
    private Integer id;
    private Integer uid;
    //用于验证时使用的uid，不能作为一般的数据使用
    private Integer onUse;

    private Integer exp;

    private Integer level;

    private Integer difficulty;

    private Integer activity;

    private Integer device;

    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOnUse() {
        return onUse;
    }

    public void setOnUse(Integer onUse) {
        this.onUse = onUse;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public RecommendStrategyInfoVO(@NonNull RecommendStrategyInfo recommendStrategyInfo) {
        this.id = recommendStrategyInfo.getId();
        this.onUse = recommendStrategyInfo.getOnUse();
        this.exp = recommendStrategyInfo.getExp();
        this.level = recommendStrategyInfo.getLevel();
        this.difficulty = recommendStrategyInfo.getDifficulty();
        this.activity = recommendStrategyInfo.getActivity();
        this.device = recommendStrategyInfo.getDevice();
        this.num = recommendStrategyInfo.getNum();
    }
}