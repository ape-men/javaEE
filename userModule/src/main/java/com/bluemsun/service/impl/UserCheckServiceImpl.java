package com.bluemsun.service.impl;

import com.bluemsun.dao.UserDao;
import com.bluemsun.enums.UserStateEnum;
import com.bluemsun.service.UserCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCheckServiceImpl implements UserCheckService {
    @Autowired
    private UserDao userDao;
    @Override
    public void loginService(String userName, String password) {

    }

}
