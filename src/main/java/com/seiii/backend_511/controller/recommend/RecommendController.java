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

    /**
     * 增加策略
     * POST: /recommendation/strategy
     * @param recommendStrategyInfoVO
     * @return
     */
    @PostMapping("/strategy")
    public ResultVO<RecommendStrategyInfoVO> addRecommendStrategy(@RequestBody RecommendStrategyInfoVO recommendStrategyInfoVO){
        return recommendStrategyInfoService.addRecommendStrategy(recommendStrategyInfoVO);
    }

    /**
     * 删除策略
     * DELETE: /recommendation/strategy?id={id}&uid={uid}
     * @param id
     * @param uid
     * @return
     */
    @DeleteMapping("/strategy")
    public ResultVO<RecommendStrategyInfoVO> deleteRecommendStrategy(@RequestParam Integer id,@RequestParam Integer uid){
        return recommendStrategyInfoService.deleteRecommendStrategy(id,uid);
    }

    /**
     * 更新策略
     * PUT: /recommendation/strategy
     * @param recommendStrategyInfoVO
     * @return
     */
    @PutMapping("/strategy")
    public ResultVO<RecommendStrategyInfoVO> updateRecommendStrategy(@RequestBody RecommendStrategyInfoVO recommendStrategyInfoVO){
        return recommendStrategyInfoService.updateRecommendStrategy(recommendStrategyInfoVO);
    }

    /**
     * 查询strategy
     * GET: /recommendation/strategy/{id}?uid={uid}
     * @param id
     * @param uid
     * @return
     */
    @GetMapping("/strategy/{id}")
    public ResultVO<RecommendStrategyInfoVO> getRecommendStrategy(@PathVariable Integer id,@RequestParam Integer uid){
        return recommendStrategyInfoService.getRecommendStrategy(id,uid);
    }

    /**
     * 设置使用策略
     * POST: recommendation/strategy/settings?id={id}&uid={uid}
     * @param id
     * @param uid
     * @return
     */
    @PostMapping("/strategy/settings")
    public ResultVO<RecommendStrategyInfoVO> putInUse(@RequestParam Integer id,@RequestParam Integer uid){
        return recommendStrategyInfoService.putInUse(id, uid);
    }


    /**
     * 获取所有策略
     * GET: recommendation/strategy?uid={uid}
     * @param uid
     * @return
     */
    @GetMapping("/strategy")
    public ResultVO<List<RecommendStrategyInfoVO>> getAll(@RequestParam Integer uid){
        return recommendStrategyInfoService.getAll(uid);
    }
}
