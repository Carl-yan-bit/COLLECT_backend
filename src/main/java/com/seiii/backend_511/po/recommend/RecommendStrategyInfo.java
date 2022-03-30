package com.seiii.backend_511.po.recommend;

import com.seiii.backend_511.vo.recommend.RecommendStrategyInfoVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RecommendStrategyInfo {
    private Integer id;

    private Integer onUse;

    private Integer exp;

    private String name;

    private Integer difficulty;

    private Integer activity;

    private Integer device;

    private Integer num;
    private Integer type;
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

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
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
    public RecommendStrategyInfo(RecommendStrategyInfoVO recommendStrategyInfo) {
        this.id = recommendStrategyInfo.getId();
        this.onUse = recommendStrategyInfo.getOnUse();
        this.exp = recommendStrategyInfo.getExp();
        this.name = recommendStrategyInfo.getname();
        this.difficulty = recommendStrategyInfo.getDifficulty();
        this.activity = recommendStrategyInfo.getActivity();
        this.device = recommendStrategyInfo.getDevice();
        this.num = recommendStrategyInfo.getNum();
        this.type = recommendStrategyInfo.getType();
    }
}