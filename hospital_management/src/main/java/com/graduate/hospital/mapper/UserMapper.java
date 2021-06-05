package com.graduate.hospital.mapper;

import com.graduate.hospital.model.User;
import com.graduate.hospital.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryUser(User user);

    UserVo selectUserInfo(String userId);

    int getCountList(Map<String, Object> map);

    List<UserVo> getUserVoList(Map<String, Object> map);

    int  deleteUser(String[] id);

    int changePassword(String userId, String newPwd);
}