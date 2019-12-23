package com.bluemsun.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bluemsun.cache.JedisUtil;
import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import com.bluemsun.enums.UserStateEnum;
import com.bluemsun.service.RegisterService;
import com.bluemsun.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@Controller
@RequestMapping("/register")
public class RegsiterController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
    @CrossOrigin
    @RequestMapping(value = "/checkoutUserName", method = RequestMethod.GET)
    @ResponseBody

    public Map<String, Object> checkOutUserName(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String userName = HttpServletRequestUtil.getString(request, "UserName");
        int count = userDao.checkOutUserName(userName);
        if (count == 1) {
            //不成功
            modelMap.put("success",UserStateEnum.REPEATMSG.getState());
            modelMap.put("info",UserStateEnum.REPEATMSG.getStateInfo());
        } else {
            //成功
            modelMap.put("success", UserStateEnum.ENABLEMSG.getState());
            modelMap.put("info",UserStateEnum.ENABLEMSG.getStateInfo());
        }
        return modelMap;
    }
    @CrossOrigin
    @RequestMapping(value = "/checkoutEmail" ,method =RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> checkOutStudentCode(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        String email =HttpServletRequestUtil.getString(request,"email");
        int count = userDao.checkOutEmail(email);
        if (count == 1) {
            //不成功
            modelMap.put("success",UserStateEnum.REPEATMSG.getState());
            modelMap.put("info",UserStateEnum.REPEATMSG.getStateInfo());
        } else {
            //成功
            modelMap.put("success", UserStateEnum.ENABLEMSG.getState());
            modelMap.put("info",UserStateEnum.ENABLEMSG.getStateInfo());
        }
        return modelMap;
    }
    @CrossOrigin
    @RequestMapping(value = "/submitRegisterInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submitRegisterInfo( HttpServletRequest request){
        User user = new User();
        Map<String ,Object> modelMap = new HashMap<>();
//        Logger logger= LoggerFactory.getLogger(RegsiterController.class);
//        logger.debug("aaaaa");
        user.setEmail(HttpServletRequestUtil.getString(request,"email"));
        user.setUserName(HttpServletRequestUtil.getString(request,"userName"));
        user.setPassword(HttpServletRequestUtil.getString(request,"password"));
        user.setQuestionAnswer(HttpServletRequestUtil.getString(request,"questionAnswer"));
        user.setSecurityQuestion(HttpServletRequestUtil.getInt(request,"securityQuestion"));
        int flag = registerService.submitRegister(user);
        if(flag>0){
            //添加成功
            modelMap.put("success",UserStateEnum.REGISTERSUCCESS.getState());
            modelMap.put("info",UserStateEnum.REGISTERSUCCESS.getStateInfo());
        }else{
            //添加不成功
            modelMap.put("success",UserStateEnum.REGISTERERROR.getState());
            modelMap.put("info",UserStateEnum.REGISTERERROR.getStateInfo());
        }
        return modelMap;
    }
    @CrossOrigin
    @RequestMapping(value = "/checkEmail",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> checkEmail(HttpServletRequest request){
        User user = new User();
        Map<String,Object> modelMap = new HashMap<>();
        String check =HttpServletRequestUtil.getString(request,"check");
        if (jedisUtilKeys.exists(check)){
            String userString=jedisUtilStrings.get(check);
            JSONObject userJSON = JSON.parseObject(userString);
            user = JSON.toJavaObject(userJSON,User.class);
            userDao.UpdateUserAvailableAndStatus(user.getUserId(),1,1);
            jedisUtilKeys.del(check);
            modelMap.put("success",UserStateEnum.REGISTERCHECKSUCCESS.getState());
            modelMap.put("info",UserStateEnum.REGISTERCHECKSUCCESS.getStateInfo());
        }else{
            modelMap.put("success",UserStateEnum.REGISTERCHECKFAIL.getState());
            modelMap.put("info",UserStateEnum.REGISTERCHECKFAIL.getStateInfo());
        }
        return modelMap;
    }

}