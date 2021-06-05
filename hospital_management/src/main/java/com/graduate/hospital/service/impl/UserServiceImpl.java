package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.UserMapper;
import com.graduate.hospital.model.User;
import com.graduate.hospital.service.UserService;
import com.graduate.hospital.vo.PageListVo;
import com.graduate.hospital.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User queryUser(User user) {
        User user1=userMapper.queryUser(user);
        return user1;
    }

    @Override
    public String insertUser(User user) {

//        检查数据库中是否已经存在该数据
        if (userMapper.selectByPrimaryKey(user.getUserId())==null) {
            int res = userMapper.insertSelective(user);
            String result = "失败！";
            if (res == 1) {
                result = "成功";
            }
            return result;
        }else {
            return   "用户已存在！";
        }
    }

//    查询用户的详细信息
    @Override
    public UserVo seltctUserInfo(String userId) {
        UserVo userVo=userMapper.selectUserInfo(userId);
        return userVo;
    }

    //修改自己的用户信息

    @Override
    public int updateByPrimaryKeySelective(User user) {
        int result=userMapper.updateByPrimaryKeySelective(user);
        return result;
    }


    @Override
    public PageListVo<UserVo> pageList(Map<String, Object> map) {
        //先拿到与map集合中user对象相匹配的总数据条数
        int count=userMapper.getCountList(map);
        List<UserVo> userVoList=userMapper.getUserVoList(map);
        PageListVo<UserVo> userVoPageListVo=new PageListVo<UserVo>();
        userVoPageListVo.setCount(count);
        userVoPageListVo.setList(userVoList);
        return userVoPageListVo;
    }

    @Override
    public boolean deleteUser(String[] id) {

        int res=userMapper.deleteUser(id);
        if (res!=0){
            return true;
        }
        return false;
    }


    //修改密码的方法

    @Override
    public boolean changePassword(String userId, String newPwd) {
        int res=userMapper.changePassword(userId,newPwd);
        if (res==1){
            return true;
        }
        return false;
    }

    //根据id查数据

    @Override
    public User selectUserByPrimaryKey(String xzUserId) {

        return userMapper.selectByPrimaryKey(xzUserId);
    }
}
