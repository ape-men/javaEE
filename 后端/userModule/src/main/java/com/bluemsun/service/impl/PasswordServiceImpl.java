package com.bluemsun.service.impl;

import com.bluemsun.cache.JedisUtil;
import com.bluemsun.service.PasswordService;
import com.bluemsun.util.EmailUtil;
import com.bluemsun.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public String sendCheckString(String email) {
        EmailUtil emailUtil = new EmailUtil();
        Random r = new Random();
        Integer temp =r.nextInt();
        String checkString=MD5Util.getMd5(temp.toString(),6);
        jedisUtilStrings.set(checkString,"checkNum");
        StringBuilder text = new StringBuilder();
        text.append("<p>用户密码修改验证码:</p>");
        text.append("<h1>");
        text.append(checkString);
        text.append("</h1>");
        emailUtil.sendMail(text.toString(),"用户修改密码验证码邮件",email,javaMailSender,true);
        return checkString;
    }
}
