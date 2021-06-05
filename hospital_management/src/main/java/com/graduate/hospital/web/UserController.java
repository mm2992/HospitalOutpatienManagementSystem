package com.graduate.hospital.web;

import com.graduate.hospital.model.Position;
import com.graduate.hospital.model.User;
import com.graduate.hospital.service.PositionService;
import com.graduate.hospital.service.UserService;
import com.graduate.hospital.vo.PageListVo;
import com.graduate.hospital.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private PositionService positionService;
    @Resource
     private UserService userService;
//    查询出所有的职位信息填充到页面
    @RequestMapping("selectAllPosition")

    public Object selectPosition(){
        List<Position> positionList=positionService.selectAllPosition();
        System.out.println(positionList.get(0));
        return positionList;
    }

//    添加用户
    @RequestMapping("addUser")
    public String insertUser(User user){
        String message=userService.insertUser(user);
        return message;
    }

    //修改当前用户自己的资料
    @RequestMapping("editMySelf")
    public boolean editUser(HttpSession session,User user){
        User user1=(User)session.getAttribute("user");
        user.setUserId(user1.getUserId());
        System.out.println(user);
        int result=userService.updateByPrimaryKeySelective(user);
        if (result==1){
            return true;
        }
        return false;
    }

    //查询用户并分页
    @RequestMapping("pageList")
    public PageListVo<UserVo> pageList(int pageNo, int pageSize, User user){
        System.out.println(user);
        /**
         * 最后需要返回总的记录条数和所有的用户信息
         * 采用vo类来进行操作
         */
        int skinPage=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("skinPage",skinPage);
        map.put("user",user);
        PageListVo<UserVo> userVoPageListVo=userService.pageList(map);
        return userVoPageListVo;

    }

    //删除用户
    @RequestMapping("deleteUser")
    public Boolean deleteUser(@RequestParam String[] id,HttpSession session){
//        System.out.println(id[0]);
        User user=(User)session.getAttribute("user");
        String userId=user.getUserId();
        System.out.println(userId);
        for (String ids:id) {
            if (ids.equals(userId)){
                return false;
            }
        }
        boolean res=userService.deleteUser(id);
        return res;
    }

    //拿到指定用户的信息以及所有的职位,将二者填充到修改用户信息的模态框
    @RequestMapping("getUserAndPosition")
    public Object getUserAndPosition( String userId){
        //根据Id查具体的用户信息
        UserVo user=userService.seltctUserInfo(userId);
//        System.out.println(user);
        //拿到所有的职位信息
        List<Position> positionList=positionService.selectAllPosition();
        Map<String,Object> map=new HashMap<>();
        map.put("user",user);
        map.put("position",positionList);
        return map;
    }

    //修改其他用户的信息
    @PostMapping("updateUser")
    public Object updateUser(User user,String xzUserId){
        int res=0;
        if (user.getUserCategory()==0){
            user.setUserCategory(null);
        }
        //根据xzUserId查询数据库中是否存在以该标号为主键的数据
        User user1=userService.selectUserByPrimaryKey(xzUserId);
        if (user.getUserId().equals(xzUserId)||user1==null) {
            res = userService.updateByPrimaryKeySelective(user);
        }
        if (res==1){
            return true;
        }
        return false;
    }


    //修改密码的方法
    @PostMapping("changePwd")
    public boolean changePwd(HttpSession session,String oldPwd,String newPwd){
        User user=(User)session.getAttribute("user");
        if (!user.getPassword().equals(oldPwd)){
            return false;
        }

        boolean result=userService.changePassword(user.getUserId(),newPwd);
        return result;

    }
}
