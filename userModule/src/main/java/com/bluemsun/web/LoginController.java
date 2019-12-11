package com.bluemsun.web;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import com.bluemsun.enums.UserStateEnum;
import com.bluemsun.service.UserCheckService;
import com.bluemsun.util.CodeUtil;
import com.bluemsun.util.HttpServletRequestUtil;
import com.bluemsun.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@Controller
@RequestMapping("/login")
public class LoginController {
   @Autowired
  private   UserDao userDao;
   @Autowired
   private UserCheckService userCheckService;
private User user;
    @RequestMapping(value = "/login" )
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> LoginCheck(HttpServletRequest request){
     Map<String,Object> modelMap = new HashMap<>();
        String userName=HttpServletRequestUtil.getString(request,"userName");
        String password=HttpServletRequestUtil.getString(request,"password");
        if(CodeUtil.checkVerifyCode(request)){
        int flag=userDao.checkOutUserName(userName);
        //写一个验证码工具类
        if(flag==0){
          //改账号未注册
        modelMap.put("success", UserStateEnum.NULLUSER.getState());
        modelMap.put("info",UserStateEnum.NULLUSER.getStateInfo());
     }else{
        user = userDao.CheckLogin(userName,password);
        if(user==null){
            //登录名或密码错误
            modelMap.put("success",UserStateEnum.ERORRMSG.getState());
            modelMap.put("info",UserStateEnum.ERORRMSG.getStateInfo());
        }else{
     if(user.getAvailable()==0){
        //邮箱未验证
        modelMap.put("success",UserStateEnum.CHECK.getState());
        modelMap.put("info",UserStateEnum.CHECK.getStateInfo());
    }else if(user.getAvailable()==2){
        //该账号已被封禁
        modelMap.put("success",UserStateEnum.OFFLINE.getState());
        modelMap.put("success",UserStateEnum.OFFLINE.getStateInfo());
    }else if(user.getAvailable()==1) {
         //账号可用
         Date editTime=userDao.CheckPasswordTime(userName,password);
         int betweenDay= TimeUtil.Betweenday(editTime);
         if(betweenDay>=90){
             //需要更换密码
             modelMap.put("success",UserStateEnum.NEEDUPDATE.getState());
             modelMap.put("info",UserStateEnum.NEEDUPDATE.getStateInfo());
         }else {
             //成功登录
             userCheckService.loginService(userName,password);
             modelMap.put("success", UserStateEnum.SUCCESS.getState());
             modelMap.put("info", UserStateEnum.SUCCESS.getStateInfo());
         }
     }
    }}}else{
            //验证码错误
            modelMap.put("success",UserStateEnum.ERRORCHECK.getState());
            modelMap.put("info",UserStateEnum.ERRORCHECK.getStateInfo());
        }
    return modelMap;
}
@RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
@CrossOrigin
    public Map<String,Object> Logout(HttpServletRequest request) {
        //request.getSession().removeAttribute("user");
    Map<String,Object> modelMap = new HashMap<>();
    modelMap.put("success",1);
    return modelMap;
}

}
