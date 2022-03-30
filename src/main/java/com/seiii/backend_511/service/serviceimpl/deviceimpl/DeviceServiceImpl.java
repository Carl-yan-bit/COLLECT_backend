package com.seiii.backend_511.service.serviceimpl.deviceimpl;

import com.seiii.backend_511.mapperservice.DeviceMapper;
import com.seiii.backend_511.po.user.Device;
import com.seiii.backend_511.service.device.DeviceService;
import com.seiii.backend_511.service.user.UserService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.user.DeviceVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
@Service
public class DeviceServiceImpl implements DeviceService {
    @Resource
    DeviceMapper deviceMapper;
    @Resource
    UserService userService;
    @Override
    public DeviceVO getDeviceById(Integer id) {
        Device d = deviceMapper.selectByPrimaryKey(id);
        if(d==null){
            return null;
        }
        return new DeviceVO(d);
    }

    @Override
    public ResultVO<DeviceVO> addNewDevice(DeviceVO deviceVO) {
        if(!userService.getUserByUid(deviceVO.getUid()).getUserRole().equals(CONST.USER_ROLE_ROOT)){
            return new ResultVO<>(CONST.REQUEST_FAIL,"添加失败,没有权限");
        }
        if(StringUtils.hasText(deviceVO.getDeviceInfo())){
            if(deviceMapper.insert(new Device(deviceVO))==1){
                return new ResultVO<>(CONST.REQUEST_SUCCESS,"添加成功",deviceVO);
            }
            return new ResultVO<>(CONST.REQUEST_FAIL,"添加失败,数据库异常");
        }
        return new ResultVO<>(CONST.REQUEST_FAIL,"添加失败");
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceMapper.selectAll();
    }
}
