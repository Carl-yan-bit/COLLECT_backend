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
    UserMapper userMapper;
    @Resource
    ProjectService projectService;

    private RecommendStrategyInfo recommendStrategyInfo;
    private List<List<Double>> mainProjectsVector;

    private List<Double> getVector(ProjectVO projectVO){
        List<Double> ans = new ArrayList<>();
        for(int i=0;i<recommendStrategyInfo.getDifficulty();i++){
            ans.add((double)projectVO.getDifficulty());
        }
        for(int i=0;i<recommendStrategyInfo.getDevice();i++){
            ans.add((double)projectVO.getDeviceId()*10);
        }
        for(int i=0;i<recommendStrategyInfo.getType();i++){
            ans.add((double)projectVO.getType()*10);
        }
        return ans;
    }

    private double calculateRank(ProjectVO projectVO){
        double ans = 0.0;
        List<Double> v = getVector(projectVO);
        for(List<Double> pMain:mainProjectsVector){
            ans = Math.max(calculate(v,pMain),ans);
        }
        return ans;
    }
    private double calculate(List<Double> v,List<Double> pMain){
        double numerator = 0.0;
        double denominator_x = 0.0;
        double denominator_y = 0.0;
        for(int i=0;i<v.size();i++){
            if(pMain.get(i) >0&& v.get(i) >0){
                //只取存在的属性计算相似度
                numerator += pMain.get(i)*v.get(i);
                denominator_x += pMain.get(i)*pMain.get(i);
                denominator_y += v.get(i)*v.get(i);
            }
        }
        return Math.sqrt(numerator*numerator/(denominator_x*denominator_y));
    }

    private List<ProjectVO> getSimilarity(){
        List<ProjectVO> all = projectService.getAllActiveProjects();
        for(ProjectVO projectVO:all){
            projectVO.setRank(calculateRank(projectVO));
        }
        all.sort((o1, o2) -> (int) (o2.getRank()*100- o1.getRank()*100));
        return all;
    }
    @Override
    public List<Project> getRecommend(Integer uid, RecommendStrategyInfo recommendStrategyInfo) {
        UserVO mainUser = new UserVO(userMapper.selectByPrimaryKey(uid));
        this.recommendStrategyInfo = recommendStrategyInfo;
        List<ProjectVO> mainProjects = projectService.getAllJoinedProjects(mainUser.getId());
        List<Integer> minID = new ArrayList<>();
        this.mainProjectsVector = new ArrayList<>();
        for(ProjectVO project: mainProjects){
            mainProjectsVector.add(getVector(project));
            minID.add(project.getId());
        }
        //计算用户加入任务的向量，避免重复计算
        List<ProjectVO> list = getSimilarity();
        List<Project> projectList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(!minID.contains(list.get(i).getId()))
            projectList.add(new Project(list.get(i)));
        }
        return projectList.subList(0,Math.min(projectList.size()-1,recommendStrategyInfo.getNum()));
    }
}
