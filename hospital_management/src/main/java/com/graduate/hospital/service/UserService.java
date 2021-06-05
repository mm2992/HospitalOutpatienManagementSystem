package com.graduate.hospital.service;

import com.graduate.hospital.model.User;
import com.graduate.hospital.vo.PageListVo;
import com.graduate.hospital.vo.UserVo;

import java.util.Map;

public interface UserService {
    User queryUser(User user);

    String insertUser(User user);

    UserVo seltctUserInfo(String UserId);

    int  updateByPrimaryKeySelective(User user);

    PageListVo<UserVo> pageList(Map<String, Object> map);

    boolean deleteUser(String[] id);

    boolean changePassword(String userId, String newPwd);

    User selectUserByPrimaryKey(String xzUserId);
}
