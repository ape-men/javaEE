package com.bluemsun.web;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import com.bluemsun.enums.UserStateEnum;
import com.bluemsun.service.UserUpdateService;
import com.bluemsun.util.HttpServletRequestUtil;
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
    private UserUpdateService userUpdateService;
    User user = new User();
    @CrossOrigin
    @RequestMapping(value = "/checkoutUserName", method = RequestMethod.GET)
    @ResponseBody

    private Map<String, Object> checkOutUserName(HttpServletRequest request) {
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
    @RequestMapping(value = "/checkoutemail" ,method =RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> checkOutStudentCode(HttpServletRequest request){
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
    private Map<String,Object> submitRegisterInfo(@RequestBody User user, HttpServletRequest request){
        Map<String ,Object> modelMap = new HashMap<>();

        int flag = userUpdateService.submitRegister();
        if(flag==1){
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
}