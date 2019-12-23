package com.bluemsun.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bluemsun.cache.JedisUtil;
import com.bluemsun.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JedisUtil.Strings jedisUtilString;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        String userString = jedisUtilString.get(token);
        JSONObject userJSON = JSON.parseObject(userString);
        User user = JSON.toJavaObject(userJSON,User.class);
        if (user.getStatus()!=2){
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.location.href=\"http://blunmsun.work:8080/personPage\"");
            out.println("</script>");
             out.println("</html>");
            return false;
        }
        return true;
    }
}
