package com.seiii.backend_511.service.serviceimpl.recommend;

import com.seiii.backend_511.mapperservice.RecommendStrategyMapper;
import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.service.recommend.RecommendStrategyInfoService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.recommend.RecommendStrategyInfoVO;
import com.seiii.backend_511.vo.user.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class RecommendStrategyInfoServiceImpl implements RecommendStrategyInfoService {
    @Resource
    private RecommendStrategyMapper recommendStrategyMapper;
    @Resource
    private UserService userService;

    @Override
    public ResultVO<RecommendStrategyInfoVO> addRecommendStrategy(RecommendStrategyInfoVO recommendStrategyInfoVO) {
        Integer uid = recommendStrategyInfoVO.getUid();
        if(!isRootUser(uid)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        recommendStrategyInfoVO.setOnUse(0);
        if(recommendStrategyMapper.selectOnUse()==null){
            recommendStrategyInfoVO.setOnUse(1);
        }
        if(recommendStrategyMapper.insert(new RecommendStrategyInfo(recommendStrategyInfoVO))==1){
            //真的插入了数据库
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",recommendStrategyInfoVO);
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"失败，未知错误");
    }

    @Override
    public ResultVO<RecommendStrategyInfoVO> deleteRecommendStrategy(Integer id, Integer uid) {
        if(!isRootUser(uid)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        if(!isValidId(id)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个策略");
        }
        if(recommendStrategyMapper.deleteByPrimaryKey(id)==1){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功");
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"失败，未知错误");
    }

    @Override
    public ResultVO<RecommendStrategyInfoVO> updateRecommendStrategy(RecommendStrategyInfoVO recommendStrategyInfoVO) {
        Integer id = recommendStrategyInfoVO.getId();
        Integer uid = recommendStrategyInfoVO.getUid();
        if(!isRootUser(uid)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        if(!isValidId(id)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个策略");
        }
        RecommendStrategyInfo old = recommendStrategyMapper.selectByPrimaryKey(id);
        recommendStrategyInfoVO.setOnUse(old.getOnUse());
        if(recommendStrategyMapper.updateByPrimaryKey(new RecommendStrategyInfo(recommendStrategyInfoVO))==1){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",recommendStrategyInfoVO);
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"失败，未知错误");
    }

    @Override
    public ResultVO<RecommendStrategyInfoVO> getRecommendStrategy(Integer id, Integer uid) {
        if(!isRootUser(uid)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        if(!isValidId(id)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个策略");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",toRecommendStrategyInfoVO(recommendStrategyMapper.selectByPrimaryKey(id)));
    }

    @Override
    public ResultVO<RecommendStrategyInfoVO> putInUse(Integer id, Integer uid) {
        if(!isRootUser(uid)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        if(!isValidId(id)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个策略");
        }
        RecommendStrategyInfo old = recommendStrategyMapper.selectOnUse();
        //原本使用的策略
        if(old!=null){
            old.setOnUse(0);
            //改为不使用
            recommendStrategyMapper.updateByPrimaryKey(old);
        }
        RecommendStrategyInfo newInfo = recommendStrategyMapper.selectByPrimaryKey(id);
        newInfo.setOnUse(1);
        if(recommendStrategyMapper.updateByPrimaryKey(newInfo)==1){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",toRecommendStrategyInfoVO(newInfo));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"失败，未知错误");
    }

    @Override
    public ResultVO<List<RecommendStrategyInfoVO>> getAll(Integer uid) {
        if(!isRootUser(uid)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有权限");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",toRecommendStrategyInfoVO(recommendStrategyMapper.selectAll()));
    }

    private boolean isRootUser(Integer uid){
        //检验用户是否是管理员
        UserVO userVO = userService.getUserByUid(uid);
        if(userVO==null){
            return false;
        }
        return userVO.getUserRole().equals(CONST.USER_ROLE_ROOT);
    }
    private boolean isValidId(Integer id){
        return recommendStrategyMapper.selectByPrimaryKey(id)!=null;
    }
    private RecommendStrategyInfoVO toRecommendStrategyInfoVO(RecommendStrategyInfo recommendStrategyInfo){
        return new RecommendStrategyInfoVO(recommendStrategyInfo);
    }
    private List<RecommendStrategyInfoVO> toRecommendStrategyInfoVO(List<RecommendStrategyInfo> recommendStrategyInfos){
        List<RecommendStrategyInfoVO> recommendStrategyInfoVOS = new LinkedList<>();
        for(RecommendStrategyInfo recommendStrategyInfo:recommendStrategyInfos){
            recommendStrategyInfoVOS.add(toRecommendStrategyInfoVO(recommendStrategyInfo));
        }
        return recommendStrategyInfoVOS;
    }
}
