package com.bluemsun.web;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import com.bluemsun.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@Controller
@RequestMapping("/personPage")
public class PersonPageController {
    @Autowired
  private   UserDao userDao;
  private   User user;
    @CrossOrigin
    @RequestMapping(value = "/personInfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> PersonInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
                if(request.getSession().getAttribute("user")==null){
            modelMap.put("success",-1);
        }else{
            user = (User)request.getSession().getAttribute("user");
            modelMap.put("success",1);
//            modelMap.put("userName",user.getUserName());
//            modelMap.put("trueName",user.getTrueName());
//            modelMap.put("StudentCode",user.getStudentCode());
//            modelMap.put("status",user.getStatus());
        }
        return modelMap;
    }
    @CrossOrigin
    @RequestMapping(value = "/updateUserPassword" , method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> updateUserPassword(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        user =(User)request.getSession().getAttribute("user");
        if (userDao.CheckUserPassword(user.getUserId(),HttpServletRequestUtil.getString(request,"oldPassword"))==0){
            //原密码错误
            modelMap.put("success",-1);
        }else{
            int i = userDao.UpdateUserPassword(user.getUserId(),HttpServletRequestUtil.getString(request,"newPassword"));
            if(i==0){
                //密码更新成功
                modelMap.put("success",1);
            }else if(i ==1){
                //新密码更新失败
                modelMap.put("success",0);
            }
        }
        return modelMap;
    }
}
