package com.seiii.backend_511.service.serviceimpl.userImpl;

import com.seiii.backend_511.mapperservice.UserDeviceMapper;
import com.seiii.backend_511.mapperservice.UserMapper;
import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.po.user.User;
import com.seiii.backend_511.po.user.UserDevice;
import com.seiii.backend_511.service.device.DeviceService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.util.Encryption;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.DeviceVO;
import com.seiii.backend_511.vo.user.UserDeviceListVO;
import com.seiii.backend_511.vo.user.UserDeviceVO;
import com.seiii.backend_511.vo.user.UserVO;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserDeviceMapper userDeviceMapper;
    @Resource
    private DeviceService deviceService;
    @Override
    public ResultVO<UserVO> userRegister(UserVO userVO) {
        userVO.setExp(0);
        userVO.setLevel(0);
        userVO.setActivity(0);
        String name = userVO.getName();
        String password = userVO.getPassword();
        String email = userVO.getEmail();
        String phoneNumber = userVO.getPhonenumber();
        if(userMapper.selectByEmail(email)==null&&userMapper.selectByPhoneNumber(phoneNumber)==null){
            if(StringUtils.hasText(name)&&StringUtils.hasText(password)){
                userVO.setCreateTime(new Date());
                //利用用户名作为盐值（lht狂怒
                userVO.setPassword(Encryption.encryptPassword(password,name));
                //创建一个持久对象
                User user = new User(userVO);
                System.out.println(new UserVO(user));
                if(userMapper.insert(user)==1)
                    return new ResultVO<>(CONST.REQUEST_SUCCESS, "账号注册成功！", new UserVO(user));
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
        if(!user.getPassword().equals(Encryption.encryptPassword(password,user.getName()))){
            return new ResultVO<>(CONST.REQUEST_FAIL, "账号或密码错误!");
        }
        return new ResultVO<>(CONST.REQUEST_SUCCESS, "登陆成功!",new UserVO(user));
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
            return new ResultVO<>(CONST.REQUEST_SUCCESS, "更改成功!",new UserVO(user));
        return new ResultVO<>(CONST.REQUEST_FAIL, "密码更改失败");
    }
    @Override
    public UserVO getUserByUid(Integer uid){
        User user = userMapper.selectByPrimaryKey(uid);
        if(user==null){
            return null;
        }
        return new UserVO(user);
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
        int level = userVO.getLevel();
        exp += Exp;
        level = (int)Math.log(exp);
        userVO.setExp(exp);
        userVO.setLevel(level);
        userMapper.updateByPrimaryKey(new User(userVO));
    }
}
