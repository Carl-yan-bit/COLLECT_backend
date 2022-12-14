package com.seiii.backend_511.service.serviceimpl.recommend.recommendStrategyImpl;

import com.seiii.backend_511.mapperservice.*;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.project.ProjectPreference;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.po.recommend.RecommendStrategyInfo;
import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.po.user.User;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.recommend.RecommendStrategy;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.project.UserProjectVO;
import com.seiii.backend_511.vo.user.DeviceVO;
import com.seiii.backend_511.vo.user.UserAttributeVO;
import com.seiii.backend_511.vo.user.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RecommendByUserCF implements RecommendStrategy {
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
    @Resource
    UserService userService;
    @Resource
    UserDeviceMapper userDeviceMapper;
    @Resource
    DeviceMapper deviceMapper;

    private UserVO mainUser;
    private RecommendStrategyInfo recommendStrategyInfo;
    private  List<Double> vMain;
    private int times=0;
    private List<Integer> getNeighbors(Integer uid){
        return userProjectMapper.getNeighbors(uid);
    }
    private List<Double> getVector(UserVO user){
        System.out.println(times);
        times++;
        UserAttributeVO userAttributeVO = userService.getUserAttributeWithoutScore(user.getId());
        List<Double> v = new ArrayList<>();
        for(int i=0;i<recommendStrategyInfo.getExp();i++){
            v.add((double) (userAttributeVO.getCapability()));
        }
        for(int i=0;i<recommendStrategyInfo.getActivity();i++){
            v.add((double) (userAttributeVO.getActivity()));
        }
        for(int i=0;i<recommendStrategyInfo.getDifficulty();i++){
            v.add((double)userAttributeVO.getTaskDifficulty());
        }
        ProjectPreference projectPreference = projectPreferenceMapper.selectByUserId(user.getId());
        List<?> ownDevices = userDeviceMapper.selectByUserId(user.getId());
        List<Device> allDevices = deviceMapper.selectAll();
        for(int i=0;i<recommendStrategyInfo.getDevice();i++){
            for(Device device:allDevices){
                double temp = 0.0;
                for(int j=0;j<ownDevices.size();j++){
                    if(((DeviceVO)(ownDevices).get(j)).getDeviceId().equals(device.getId())){
                        temp=100.0;
                        break;
                    }
                }
                v.add(temp);
            }

        }
        for(int i=0;i<recommendStrategyInfo.getType();i++){
            if(projectPreference.getType()==null){
                v.add((double)0);
            }
            else {
                v.add((double) (projectPreference.getType()*10));
            }
        }
        return v;
    }
    private List<Double> getVectorOld(UserVO user){

        List<Double> v = new ArrayList<>();
        for(int i=0;i<recommendStrategyInfo.getExp();i++){
            v.add((double) (user.getExp()/10));
        }
        for(int i=0;i<recommendStrategyInfo.getActivity();i++){
            v.add((double) (user.getActivity()));
        }
        ProjectPreference projectPreference = projectPreferenceMapper.selectByUserId(user.getId());
        for(int i=0;i<recommendStrategyInfo.getDifficulty();i++){
            if(projectPreference.getDifficulty()==null){
                v.add((double)-1);
            }
            else {
                v.add((double) (projectPreference.getDifficulty()));
            }
        }
        for(int i=0;i<recommendStrategyInfo.getDevice();i++){
            if(projectPreference.getDeviceId()==null){
                v.add((double)-1);
            }
            else {
                v.add((double) (projectPreference.getDeviceId()));
            }
        }
        for(int i=0;i<recommendStrategyInfo.getType();i++){
            if(projectPreference.getType()==null){
                v.add((double)-1);
            }
            else {
                v.add((double) (projectPreference.getType()));
            }
        }
        return v;
    }
    private double calculate(UserVO user){
        List<Double> v = getVector(user);
        double numerator = 0.0;
        double denominator_x = 0.0;
        double denominator_y = 0.0;
        for(int i=0;i<v.size();i++){
            if(vMain.get(i) >0&& v.get(i) >0){
                //????????????????????????????????????
                numerator += vMain.get(i)*v.get(i);
                denominator_x += vMain.get(i)*vMain.get(i);
                denominator_y += v.get(i)*v.get(i);
            }
        }
        return Math.sqrt(numerator*numerator/(denominator_x*denominator_y));
    }
    private List<UserVO> getSimilarity(){
        List<Integer> uidList = getNeighbors(mainUser.getId());
        List<UserVO> userVOList = new ArrayList<>();
        //???????????????????????????????????????
        for(Integer targetUid:uidList){
            UserVO user = new UserVO(userMapper.selectByPrimaryKey(targetUid));
            user.setSimilarity(calculate(user));
            userVOList.add(user);
        }
        userVOList.sort((o1, o2) -> (int) (o2.getSimilarity()*100- o1.getSimilarity()*100));
        return userVOList;
    }

    @Override
    public List<Project> getRecommend(Integer uid, RecommendStrategyInfo recommendStrategyInfo) {
        this.mainUser = new UserVO(userMapper.selectByPrimaryKey(uid));
        this.recommendStrategyInfo = recommendStrategyInfo;
        this.vMain = getVector(mainUser);
        List<Project> ans = new ArrayList<>();
        List<UserVO> userVOS = getSimilarity();
        List<UserProjectVO> allProject = new ArrayList<>();
        for(int i=0;i<Math.min(userVOS.size(),5);i++){
            //???????????????????????????????????????
            for(UserProject userProject:userProjectMapper.selectByUser(userVOS.get(i).getId())){
                UserProjectVO userProjectVO = new UserProjectVO(userProject);
                userProjectVO.setRank(userVOS.get(i).getSimilarity());
                boolean flag = false;
                for (UserProjectVO projectVO : allProject) {
                    if (userProjectVO.getProjectId().equals(projectVO.getProjectId())) {
                        flag = true;
                        projectVO.setRank(projectVO.getRank() + userProjectVO.getRank());
                        break;
                    }
                }
                if(!flag){
                    allProject.add(userProjectVO);
                }
            }
        }
        allProject.sort((o1, o2) -> (int) (o2.getRank()*100- o1.getRank()*100));
        int idx = 0;
        while (ans.size()<recommendStrategyInfo.getNum()){
            if(idx>=allProject.size()){
                break;
            }
            Project project = projectMapper.selectByPrimaryKey(allProject.get(idx).getProjectId());
            if(userProjectMapper.selectByProjectAndUser(project.getId(), mainUser.getId())==null&&projectService.isActive(project)){
                ans.add(project);
                //?????????????????????????????????????????????
            }
            idx++;
        }
        if (ans.size()<recommendStrategyInfo.getNum()){
            for(Project project:projectMapper.selectAllByClickOrder(recommendStrategyInfo.getNum(), mainUser.getId())){
                if(!ans.contains(project)){
                    ans.add(project);
                }
                if(ans.size()>=recommendStrategyInfo.getNum()){
                    break;
                }
            }
        }
        return ans;
    }
}
