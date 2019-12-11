package com.bluemsun.web;
import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.User;
import com.bluemsun.service.PageService;
import com.bluemsun.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@Controller
@RequestMapping("/superAdmin")
public class SuperAdminController {
    @Autowired
   private UserDao userDao;
    @Autowired
    private PageService pageService;
   private User user;
   private Page page;
    @CrossOrigin
    @RequestMapping(value = "/listNowUser" ,method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listAllUser(HttpServletRequest request){
        List<User> userList;
        Map<String,Object> modelMap = new HashMap<>();
        User user=(User)request.getSession().getAttribute("user");
        if(user.getStatus()==1){
        int pageNum = HttpServletRequestUtil.getInt(request,"pageNum");
        page=pageService.userPage(pageNum);
        userList=userDao.listNowUser(page.getStartIndex(),page.getPageSize());
        modelMap.put("userList",userList);
        modelMap.put("success",1);
        modelMap.put("pageNum",pageNum);
        modelMap.put("pages",page.getTotalPage());
        modelMap.put("total",page.getTotalRecord());}
        else{
            modelMap.put("success",0);
        }
        return modelMap;
    }
    @CrossOrigin
    @RequestMapping(value = "/updateUserStatusAndAvailable",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> updateUserStatusAndAvailable(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        int userId = HttpServletRequestUtil.getInt(request,"userId");
        int status = HttpServletRequestUtil.getInt(request,"status");
        int available = HttpServletRequestUtil.getInt(request,"available");
        int flag=userDao.UpdateUserAvailableAndStatus(userId,available,status);
        if(flag==1){
            modelMap.put("success",1);
        }else{
            modelMap.put("success",0);
        }
        return modelMap;
    }
}
