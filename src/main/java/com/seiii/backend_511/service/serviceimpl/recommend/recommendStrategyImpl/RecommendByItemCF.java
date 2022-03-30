package com.seiii.backend_511.service.serviceimpl.recommend.recommendStrategyImpl;

import com.seiii.backend_511.mapperservice.ProjectMapper;
import com.seiii.backend_511.mapperservice.ProjectPreferenceMapper;
import com.seiii.backend_511.mapperservice.UserMapper;
import com.seiii.backend_511.mapperservice.UserProjectMapper;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.recommend.RecommendStrategy;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.user.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class RecommendByItemCF implements RecommendStrategy {
    @Resource
    UserProjectMapper userProjectMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ProjectMapper projectMapper;
    @Resource
    ProjectService projectService;
    @Resource
    ProjectPreferenceMapper projectPreferenceMapper;

    private UserVO mainUser;
    private RecommendStrategyInfo recommendStrategyInfo;
    private  List<Double> vMain;
    private List<ProjectVO> mainProjects;

    private List<Double> getVector(ProjectVO projectVO){
        List<Double> ans = new ArrayList<>();
        ans.add((double)projectVO.getDifficulty());
        return null;
    }

    @Override
    public List<Project> getRecommend(Integer uid, RecommendStrategyInfo recommendStrategyInfo) {
        this.mainUser = new UserVO(userMapper.selectByPrimaryKey(uid));
        this.recommendStrategyInfo = recommendStrategyInfo;
        this.mainProjects = projectService.getAllJoinedProjects(mainUser.getId());

        return null;
    }
}
