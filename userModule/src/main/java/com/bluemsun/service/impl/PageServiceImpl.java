package com.bluemsun.service.impl;

import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.Page;
import com.bluemsun.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private UserDao userDao;
   public Page userPage(int pageNum){
        int totalRecord = userDao.getUserCount();
        int pageSize = 15;
        Page page = new Page(pageNum,pageSize,totalRecord);
        return page;
    }
}
