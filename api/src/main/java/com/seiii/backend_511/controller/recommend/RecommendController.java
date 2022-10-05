package com.seiii.backend_511.controller.recommend;

import com.seiii.backend_511.service.recommend.RecommendStrategyInfoService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.recommend.RecommendStrategyInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendController {
    @Resource
    private RecommendStrategyInfoService recommendStrategyInfoService;
    @PostMapping("/strategy")
    public ResultVO<RecommendStrategyInfoVO> addRecommendStrategy(@RequestBody RecommendStrategyInfoVO recommendStrategyInfoVO){
        return recommendStrategyInfoService.addRecommendStrategy(recommendStrategyInfoVO);
    }
    @DeleteMapping("/strategy")
    public ResultVO<RecommendStrategyInfoVO> deleteRecommendStrategy(@RequestParam Integer id,@RequestParam Integer uid){
        return recommendStrategyInfoService.deleteRecommendStrategy(id,uid);
    }
    @PutMapping("/strategy")
    public ResultVO<RecommendStrategyInfoVO> updateRecommendStrategy(@RequestBody RecommendStrategyInfoVO recommendStrategyInfoVO){
        return recommendStrategyInfoService.updateRecommendStrategy(recommendStrategyInfoVO);
    }
    @GetMapping("/strategy/{id}")
    public ResultVO<RecommendStrategyInfoVO> getRecommendStrategy(@PathVariable Integer id,@RequestParam Integer uid){
        return recommendStrategyInfoService.getRecommendStrategy(id,uid);
    }
    @PostMapping("/strategy/settings")
    public ResultVO<RecommendStrategyInfoVO> putInUse(@RequestParam Integer id,@RequestParam Integer uid){
        return recommendStrategyInfoService.putInUse(id, uid);
    }
    @GetMapping("/strategy")
    public ResultVO<List<RecommendStrategyInfoVO>> getAll(@RequestParam Integer uid){
        return recommendStrategyInfoService.getAll(uid);
    }
}
