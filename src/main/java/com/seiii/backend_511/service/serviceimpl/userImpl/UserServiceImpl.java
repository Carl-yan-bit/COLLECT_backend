package com.seiii.backend_511.service.serviceimpl.userImpl;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.mapperservice.*;
import com.seiii.backend_511.po.UserLog;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.project.ProjectPreference;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.po.report.Report;
import com.seiii.backend_511.po.report.ReportComment;
import com.seiii.backend_511.po.report.ReportSimilar;
import com.seiii.backend_511.po.task.UserTask;
import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.po.user.User;
import com.seiii.backend_511.po.user.UserDevice;
import com.seiii.backend_511.service.device.DeviceService;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.service.report.ReportCommentService;
import com.seiii.backend_511.service.report.ReportService;
import com.seiii.backend_511.service.task.TaskService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.Encryption;
import com.seiii.backend_511.util.PageInfoUtil;
import com.seiii.backend_511.util.PmmlHelper;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.report.ReportCommentVO;
import com.seiii.backend_511.vo.report.ReportSimilarVO;
import com.seiii.backend_511.vo.report.ReportVO;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.user.*;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.TargetField;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserDeviceMapper userDeviceMapper;
    @Resource
    private DeviceService deviceService;
    @Resource
    private UserLogMapper userLogMapper;
    @Resource
    private ProjectPreferenceMapper projectPreferenceMapper;
    @Resource
    private ReportService reportService;
    @Resource
    private ReportCommentService reportCommentService;
    @Resource
    private TaskService taskService;
    @Resource
    private ProjectService projectService;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private UserProjectMapper userProjectMapper;
    private PmmlHelper helper = null;
    private void newUserProjectPreference(User user){
        User userReal = userMapper.selectByEmail(user.getEmail());
        ProjectPreference projectPreference = new ProjectPreference();
        projectPreference.setUserId(userReal.getId());
        projectPreferenceMapper.insert(projectPreference);
    }
    @Override
    public ResultVO<UserVO> userRegister(UserVO userVO) {
        userVO.setExp(0);
        userVO.setLevel(0);
        userVO.setActivity(0);
        String name = userVO.getName();
        String password = userVO.getPassword();
        String email = userVO.getEmail();
        String phoneNumber = userVO.getPhonenumber();
        if(!StringUtils.hasText(userVO.getUserRole())){
            return new ResultVO<>(CONST.REQUEST_FAIL, "?????????????????????");
        }
        if(userMapper.selectByName(userVO.getName())!=null){
            return new ResultVO<>(CONST.REQUEST_FAIL, "?????????????????????");
        }
        if(userMapper.selectByEmail(email)==null&&userMapper.selectByPhoneNumber(phoneNumber)==null){
            if(StringUtils.hasText(name)&&StringUtils.hasText(password)){
                userVO.setCreateTime(new Date());
                //??????????????????????????????lht??????
                userVO.setPassword(Encryption.encryptPassword(password,name));
                //????????????????????????
                User user = new User(userVO);
                System.out.println(toUserVO(user));
                if(userMapper.insert(user)==1){
                    newUserProjectPreference(user);
                    return new ResultVO<>(CONST.REQUEST_SUCCESS, "?????????????????????", toUserVO(user));
                }
                return new ResultVO<>(CONST.REQUEST_FAIL, "????????????");
            }
            else {
                return new ResultVO<>(CONST.REQUEST_FAIL, "??????????????????????????????!");
            }
        }
        else {
            return new ResultVO<>(CONST.REQUEST_FAIL, "????????????????????????????????????!");
        }
    }

    @Override
    public ResultVO<UserVO> userLogin(String user_idx,String password) {
        User user = null;
        if(user_idx.contains("@")){
            if(userMapper.selectByEmail(user_idx)!=null)
                user = userMapper.selectByEmail(user_idx);
        }
        else {
            if(userMapper.selectByPhoneNumber(user_idx)!=null)
                user = userMapper.selectByPhoneNumber(user_idx);
        }
        if(user==null){
            return new ResultVO<>(CONST.REQUEST_FAIL, "??????????????????????????????????????????!");
        }
        UserLog userLog = new UserLog(user.getId(),"??????",CONST.LOGIN_POINT,new Date());
        userLogMapper.insert(userLog);
        if(!user.getPassword().equals(Encryption.encryptPassword(password,user.getName()))){
            return new ResultVO<>(CONST.REQUEST_FAIL, "?????????????????????!");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS, "????????????!",toUserVO(user));
    }

    @Override
    public ResultVO<UserVO> userChangePassword(String user_idx,String password_old,String password_new) {
        User user;
        if(user_idx.contains("@")){
            user = userMapper.selectByEmail(user_idx);
        }
        else {
            user = userMapper.selectByPhoneNumber(user_idx);
        }
        if(user==null){
            return new ResultVO<>(CONST.REQUEST_FAIL, "??????????????????????????????????????????!");
        }
        if(!user.getPassword().equals(Encryption.encryptPassword(password_old,user.getName()))){
            return new ResultVO<>(CONST.REQUEST_FAIL, "????????????!");
        }
        user.setPassword(Encryption.encryptPassword(password_new,user.getName()));
        if(userMapper.updateByPrimaryKey(user)==1)
            return new ResultVO<>(CONST.REQUEST_SUCCESS, "????????????!",toUserVO(user));
        return new ResultVO<>(CONST.REQUEST_FAIL, "??????????????????");
    }
    @Override
    public UserVO getUserByUid(Integer uid){
        User user = userMapper.selectByPrimaryKey(uid);
        if(user==null){
            return null;
        }
        return toUserVO(user);
    }

    @Override
    public ResultVO<UserVO> getUserByUidWithCode(Integer uid) {
        UserVO userVO = getUserByUid(uid);
        if(userVO==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"????????????");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",userVO);
    }

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public ResultVO<List<Device>> getUserDevices(Integer uid) {
        if(userDeviceMapper.selectByUserId(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",userDeviceMapper.selectByUserId(uid));
    }

    @Override
    public ResultVO<DeviceVO> addUserDevice(UserDeviceVO userDeviceVO) {
        if(getUserByUid(userDeviceVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(userDeviceMapper.selectByUserAndDevice(userDeviceVO.getUserId(),userDeviceVO.getDeviceId())!=null){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",deviceService.getDeviceById(userDeviceMapper.selectByUserAndDevice(userDeviceVO.getUserId(),userDeviceVO.getDeviceId()).getDeviceId()));
        }
        if(userDeviceMapper.insert(new UserDevice(userDeviceVO))==1){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",deviceService.getDeviceById(userDeviceMapper.selectByUserAndDevice(userDeviceVO.getUserId(),userDeviceVO.getDeviceId()).getDeviceId()));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"??????");
    }

    @Override
    public ResultVO<DeviceVO> deleteUserDevice(UserDeviceVO userDeviceVO) {
        if(getUserByUid(userDeviceVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????????????????");
        }
        if(!userDeviceVO.getUserId().equals(userDeviceMapper.selectByPrimaryKey(userDeviceVO.getId()).getUserId())){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????,????????????");
        }
        DeviceVO deviceVO = deviceService.getDeviceById(userDeviceMapper.selectByPrimaryKey(userDeviceVO.getId()).getDeviceId());
        if(userDeviceMapper.deleteByPrimaryKey(userDeviceVO.getId())==1){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",deviceVO);
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"??????");
    }

    @Override
    public ResultVO<List<DeviceVO>> addUserDevice(UserDeviceListVO userDeviceVOList) {
        List<DeviceVO> list=new ArrayList<>();
        userDeviceMapper.deleteByUid(userDeviceVOList.getUid());
        for(int i=0;i<userDeviceVOList.getList().size();i++){
            UserDeviceVO userDeviceVO = new UserDeviceVO();
            userDeviceVO.setUserId(userDeviceVOList.getUid());
            userDeviceVO.setDeviceId(userDeviceVOList.getList().get(i));
            ResultVO<DeviceVO> temp = addUserDevice(userDeviceVO);
            if(temp.getCode().equals(CONST.REQUEST_FAIL)){
                return new ResultVO<>(CONST.REQUEST_FAIL,"??????");
            }
            else {
                list.add(temp.getData());
            }
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"??????",list);
    }

    @Override
    public void getExp(UserVO userVO, int Exp) {
        int exp = userVO.getExp();
        int level;
        exp += Exp;
        level = (int)Math.log(exp);
        userVO.setExp(exp);
        userVO.setLevel(level);
        userMapper.updateByPrimaryKey(new User(userVO));
    }


    private UserVO toUserVO(User user){
        UserVO userVO = new UserVO(user);
        int expRequire = (int) (Math.exp(user.getLevel()+1)-user.getExp());
        if(expRequire<0){
            userVO.setLevel(userVO.getLevel()+1);
            expRequire = (int) (Math.exp(userVO.getLevel()+1)-user.getExp());
        }
        userVO.setExpRequire(expRequire);
        return userVO;
    }
    @Override
    public UserAttributeVO getUserAttributeWithoutScore(Integer uid){
        int activity=getActivity(uid);
        String preference=getPerference(uid);
        int capability=getCapability(uid);
        int assistance=getAssistance(uid);
        int examination=getExamination(uid);
        int reportPoint=getReportPoint(uid);
        int discovery=getDiscovery(uid);
        int taskDifficulty=getTaskDifficulty(uid);
        double totalScore=0.0;
        UserAttributeVO userAttributeVO=new UserAttributeVO(capability,
                preference,
                activity,
                assistance,
                examination,
                reportPoint,
                discovery,
                taskDifficulty,
                totalScore);
        if(!userAttributeVO.isVaild()){
            return null;
        }
        return userAttributeVO;
    }
    @Override
    public ResultVO<UserAttributeVO> getUserAttribute(Integer uid) {
        if(helper==null)
            helper = new PmmlHelper("file/regression.pmml");
        int activity=getActivity(uid);
        String preference=getPerference(uid);
        int capability=getCapability(uid);
        int assistance=getAssistance(uid);
        int examination=getExamination(uid);
        int reportPoint=getReportPoint(uid);
        int discovery=getDiscovery(uid);
        int taskDifficulty=getTaskDifficulty(uid);
        double totalScore=getTotalScore(activity,capability,assistance,examination,reportPoint,discovery,taskDifficulty);
        UserAttributeVO userAttributeVO=new UserAttributeVO(capability,
                preference,
                activity,
                assistance,
                examination,
                reportPoint,
                discovery,
                taskDifficulty,
                totalScore);
        if(!userAttributeVO.isVaild()){
            return new ResultVO<>(CONST.REQUEST_FAIL,"??????uid??????");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"????????????",userAttributeVO);
    }


    private double getTotalScore(int activity,int capability,int assistance,int examination,int reportPoint,int discovery,int taskDifficulty){

        Map<String,Integer> input = new HashMap<>();
        input.put("x1",capability);
        input.put("x2",activity);
        input.put("x3",assistance);
        input.put("x4",examination);
        input.put("x5",reportPoint);
        input.put("x6",discovery);
        input.put("x7",taskDifficulty);
        Map<FieldName,?> res = helper.predict(input);
        FieldName fieldName = FieldName.create("y");
        return (Double) res.get(fieldName);
    }

    private String getPerference(Integer uid){
        HashMap<Integer,Integer> typeMap=new HashMap<>();
        List<UserProject> userProjects=userProjectMapper.selectByUser(uid);
        for(UserProject userProject:userProjects){
            ProjectVO projectVO=projectService.getProjectById(userProject.getProjectId());
            if(typeMap.containsKey(projectVO.getType())){
                typeMap.put(projectVO.getType(),typeMap.get(projectVO.getType()));
            }else {
                typeMap.put(projectVO.getType(),1);
            }
        }
        List<Map.Entry<Integer,Integer>> typeList=new ArrayList<>(typeMap.entrySet());
        if(typeList.size()==0){
            return "???????????????????????????????????????";
        }
        int max=0,maxpos=0;
        for(int i=0;i<typeList.size();i++){
            int temp=typeList.get(i).getValue();
            if(typeList.get(i).getValue()>max){
                maxpos=typeList.get(i).getKey();
            }
        }
        return typeMapper.selectByPrimaryKey(maxpos).getTypeInfo();
    }

    private int getCapability(Integer uid){
        ResultVO<List<ReportVO>> reports=reportService.getReportsByUID(uid);
        if(reports.getCode()!=CONST.REQUEST_SUCCESS){
            return -1;
        }
        List<ReportVO> reportVOS=reports.getData();
        Double sum=0.0;
        for(int i=0;i<reportVOS.size();i++){
            boolean isNew=true;
            ReportVO reportVO=reportVOS.get(i);
            List<ReportSimilarVO> reportSimilarVOS=reportService.getSimilarReport(reportVO).getData();
            if(reportSimilarVOS!=null){
                for (int j=0;j<reportSimilarVOS.size();j++){
                    ReportSimilarVO reportSimilarVO=reportSimilarVOS.get(j);
                    if(reportSimilarVO.getSimilarity()>0.6){
                        isNew=false;
                    }
                    if(!isNew){
                        sum+=reportSimilarVO.getSimilarity()*3*reportSimilarVO.getScore();
                    }
                }
            }
            if(isNew){
                sum+=5*reportVO.getScore();
            }
        }
        if(sum>100){
            return 100;
        }
        return sum.intValue();
    }

    private int getActivity(Integer uid){
        ResultVO<List<ReportVO>> reports=reportService.getReportsByUID(uid);
        ResultVO<List<ReportCommentVO>> reportComments=reportCommentService.getCommentsByUID(uid);
        if(reports.getCode()!=CONST.REQUEST_SUCCESS || reportComments.getCode()!=CONST.REQUEST_SUCCESS){
            return -1;
        }
        List<ReportVO> reportVOS=reports.getData();
        List<ReportCommentVO> reportCommentVOS=reportComments.getData();
        Date today=new Date();
        int activity=0;
        int weekIntervalActivity=0;
        int week2IntervalActivity=0;
        for(int i=0;i<reportVOS.size();i++){
            if(activity>=100){
                break;
            }
            ReportVO curVO=reportVOS.get(i);
            long intervalDay=(today.getTime()-curVO.getCreateTime().getTime())/(1000*60*60*24);
            if(intervalDay>14){
                if(week2IntervalActivity<20){
                    week2IntervalActivity++;
                }
            }else if (intervalDay>7){
                if(weekIntervalActivity<30){
                    weekIntervalActivity+=3;
                }
            }else {
                activity+=5;
            }
        }
        for(int i=0;i<reportCommentVOS.size();i++){
            if(activity>=100){
                break;
            }
            ReportCommentVO curVO=reportCommentVOS.get(i);
            long intervalDay=(today.getTime()-curVO.getCreateTime().getTime())/(1000*60*60*24);
            if(intervalDay>14){
                if(week2IntervalActivity<20){
                    week2IntervalActivity++;
                }
            }else if (intervalDay>7){
                if(weekIntervalActivity<30){
                    weekIntervalActivity+=3;
                }
            }else {
                activity+=5;
            }
        }
        activity=weekIntervalActivity+week2IntervalActivity+activity;
        if(activity>100){
            return 100;
        }return activity;
    }

    private int getAssistance(Integer uid){
        ResultVO<List<ReportVO>> reports=reportService.getReportsByUID(uid);
        if(reports.getCode()!=CONST.REQUEST_SUCCESS){
            return -1;
        }
        List<ReportVO> reportVOS=reports.getData();
        Double sum=0.0;
        for(ReportVO reportVO:reportVOS){
            if(reportVO.getParentReport()!=null){
                sum+=reportVO.getScore();
            }
        }
        sum=sum*20/reportVOS.size();
        if(sum>100){
            return 100;
        }
        return sum.intValue();
    }

    private int getExamination(Integer uid){
        ResultVO<List<ReportCommentVO>> reportComments=reportCommentService.getCommentsByUID(uid);
        if(reportComments.getCode()!=CONST.REQUEST_SUCCESS){
            return -1;
        }
        List<ReportCommentVO> reportCommentVOS=reportComments.getData();
        if(reportCommentVOS.size()==0){
            return 0;
        }
        Double sum=0.0;
        for(ReportCommentVO reportCommentVO:reportCommentVOS){
            ReportVO reportVO=reportService.getReportByID(reportCommentVO.getReportId()).getData();
            Double plus= Double.valueOf(2/(Math.abs(reportVO.getScore()-reportCommentVO.getScore()+0.1)));
            if(plus>10){
                sum+=10;
            }else {
                sum+=plus;
            }
        }
        if(sum>100){
            return 100;
        }
        return sum.intValue();
    }

    private int getReportPoint(Integer uid){
        ResultVO<List<ReportVO>> reports=reportService.getReportsByUID(uid);
        if(reports.getCode()!=CONST.REQUEST_SUCCESS){
            return -1;
        }
        List<ReportVO> reportVOS=reports.getData();
        if(reportVOS.size()==0){
            return 0;
        }
        Double sum=0.0;
        for(ReportVO reportVO:reportVOS){
            sum+=reportVO.getScore();
        }
        sum=sum*20/reportVOS.size();
        if(sum>100){
            return 100;
        }
        return sum.intValue();
    }

    private int getDiscovery(Integer uid){
        ResultVO<List<ReportVO>> reports=reportService.getReportsByUID(uid);
        if(reports.getCode()!=CONST.REQUEST_SUCCESS){
            return -1;
        }
        HashSet<Integer> projectSet=new HashSet<>();
        List<ReportVO> reportVOS=reports.getData();
        if(reportVOS.size()==0){
            return 0;
        }
        int projectCount=0;
        for(ReportVO reportVO:reportVOS){
            int taskId=reportVO.getTaskId();
            TaskVO taskVO=taskService.getTaskByID(taskId);
            if(!projectSet.contains(taskVO.getProjectId())){
                projectCount++;
                projectSet.add(taskVO.getProjectId());
            }
        }
        int sum=reportVOS.size()/projectCount*10;
        if(sum>100){
            return 100;
        }
        return sum;
    }

    private int getTaskDifficulty(Integer uid){
        ResultVO<List<ReportVO>> reports=reportService.getReportsByUID(uid);
        if(reports.getCode()!=CONST.REQUEST_SUCCESS){
            return -1;
        }
        List<ReportVO> reportVOS=reports.getData();
        if(reportVOS.size()==0){
            return 0;
        }
        Double sum=0.0;
        for(ReportVO reportVO:reportVOS){
            int taskId=reportVO.getTaskId();
            TaskVO taskVO=taskService.getTaskByID(taskId);
            ProjectVO projectVO=projectService.getProjectById(taskVO.getProjectId());
            sum+=projectVO.getDifficulty();
        }
        sum=sum*10/reportVOS.size();
        if(sum>100){
            return 100;
        }
        return sum.intValue();
    }

    public ResultVO<List<UserVO>> getAllUsers() {
        List<UserVO> userVOList = new ArrayList<>();
        for(User user:userMapper.selectAll()){
            UserVO u = toUserVO(user);
            userVOList.add(u);
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS, "??????", userVOList);
    }
}
