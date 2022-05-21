package com.seiii.backend_511.service.serviceimpl.userImpl;

import com.seiii.backend_511.mapperservice.*;
import com.seiii.backend_511.po.UserLog;
import com.seiii.backend_511.po.project.ProjectPreference;
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
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.report.ReportCommentVO;
import com.seiii.backend_511.vo.report.ReportSimilarVO;
import com.seiii.backend_511.vo.report.ReportVO;
import com.seiii.backend_511.vo.task.TaskVO;
import com.seiii.backend_511.vo.user.*;

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
    private UserTaskMapper userTaskMapper;

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
            return new ResultVO<>(CONST.REQUEST_FAIL, "请选择用户角色");
        }
        if(userMapper.selectByName(userVO.getName())!=null){
            return new ResultVO<>(CONST.REQUEST_FAIL, "用户名已被占用");
        }
        if(userMapper.selectByEmail(email)==null&&userMapper.selectByPhoneNumber(phoneNumber)==null){
            if(StringUtils.hasText(name)&&StringUtils.hasText(password)){
                userVO.setCreateTime(new Date());
                //利用用户名作为盐值（lht狂怒
                userVO.setPassword(Encryption.encryptPassword(password,name));
                //创建一个持久对象
                User user = new User(userVO);
                System.out.println(toUserVO(user));
                if(userMapper.insert(user)==1){
                    newUserProjectPreference(user);
                    return new ResultVO<>(CONST.REQUEST_SUCCESS, "账号注册成功！", toUserVO(user));
                }
                return new ResultVO<>(CONST.REQUEST_FAIL, "注册失败");
            }
            else {
                return new ResultVO<>(CONST.REQUEST_FAIL, "用户名或密码不得为空!");
            }
        }
        else {
            return new ResultVO<>(CONST.REQUEST_FAIL, "该手机号或邮箱已经被注册!");
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
            return new ResultVO<>(CONST.REQUEST_FAIL, "尚未注册，请检查输入或先注册!");
        }
        UserLog userLog = new UserLog(user.getId(),"登陆",CONST.LOGIN_POINT,new Date());
        userLogMapper.insert(userLog);
        if(!user.getPassword().equals(Encryption.encryptPassword(password,user.getName()))){
            return new ResultVO<>(CONST.REQUEST_FAIL, "账号或密码错误!");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS, "登陆成功!",toUserVO(user));
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
            return new ResultVO<>(CONST.REQUEST_FAIL, "尚未注册，请检查输入或先注册!");
        }
        if(!user.getPassword().equals(Encryption.encryptPassword(password_old,user.getName()))){
            return new ResultVO<>(CONST.REQUEST_FAIL, "密码错误!");
        }
        user.setPassword(Encryption.encryptPassword(password_new,user.getName()));
        if(userMapper.updateByPrimaryKey(user)==1)
            return new ResultVO<>(CONST.REQUEST_SUCCESS, "更改成功!",toUserVO(user));
        return new ResultVO<>(CONST.REQUEST_FAIL, "密码更改失败");
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
            return new ResultVO<>(CONST.REQUEST_FAIL,"查询失败");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"查询成功",userVO);
    }

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public ResultVO<List<Device>> getUserDevices(Integer uid) {
        if(userDeviceMapper.selectByUserId(uid)==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"用户没有设备");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",userDeviceMapper.selectByUserId(uid));
    }

    @Override
    public ResultVO<DeviceVO> addUserDevice(UserDeviceVO userDeviceVO) {
        if(getUserByUid(userDeviceVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(userDeviceMapper.selectByUserAndDevice(userDeviceVO.getUserId(),userDeviceVO.getDeviceId())!=null){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",deviceService.getDeviceById(userDeviceMapper.selectByUserAndDevice(userDeviceVO.getUserId(),userDeviceVO.getDeviceId()).getDeviceId()));
        }
        if(userDeviceMapper.insert(new UserDevice(userDeviceVO))==1){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",deviceService.getDeviceById(userDeviceMapper.selectByUserAndDevice(userDeviceVO.getUserId(),userDeviceVO.getDeviceId()).getDeviceId()));
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"失败");
    }

    @Override
    public ResultVO<DeviceVO> deleteUserDevice(UserDeviceVO userDeviceVO) {
        if(getUserByUid(userDeviceVO.getUserId())==null){
            return new ResultVO<>(CONST.REQUEST_FAIL,"没有这个用户");
        }
        if(!userDeviceVO.getUserId().equals(userDeviceMapper.selectByPrimaryKey(userDeviceVO.getId()).getUserId())){
            return new ResultVO<>(CONST.REQUEST_FAIL,"失败,没有权限");
        }
        DeviceVO deviceVO = deviceService.getDeviceById(userDeviceMapper.selectByPrimaryKey(userDeviceVO.getId()).getDeviceId());
        if(userDeviceMapper.deleteByPrimaryKey(userDeviceVO.getId())==1){
            return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",deviceVO);
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"失败");
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
                return new ResultVO<>(CONST.REQUEST_FAIL,"失败");
            }
            else {
                list.add(temp.getData());
            }
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"成功",list);
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
    public ResultVO<UserAttributeVO> getUserAttribute(Integer uid) {
        int activity=getActivity(uid);
        String preference=getPerference(uid);
        int capability=getCapability(uid);
        int assistance=getAssistance(uid);
        int examination=getExamination(uid);
        int reportPoint=getReportPoint(uid);
        int discovery=getDiscovery(uid);
        int taskDifficulty=getTaskDifficulty(uid);
        int totalScore=0;//todo 计算总评分
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
            return new ResultVO<>(CONST.REQUEST_FAIL,"请求uid错误",null);
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS,"请求成功",userAttributeVO);
    }

    private String getPerference(Integer uid){
        HashMap<Integer,Integer> typeMap=new HashMap<>();
        List<UserTask> userTasks=userTaskMapper.selectByUid(uid);
        for(UserTask userTask:userTasks){
            TaskVO taskVO=taskService.getTaskByID(userTask.getTaskId());
            if(typeMap.containsKey(taskVO.getType())){
                typeMap.put(taskVO.getType(),typeMap.get(taskVO.getType()));
            }else {
                typeMap.put(taskVO.getType(),1);
            }
        }
        List<Map.Entry<Integer,Integer>> typeList=new ArrayList<>(typeMap.entrySet());
        if(typeList.size()==0){
            return "用户未参加任务，无任务偏好";
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
}
