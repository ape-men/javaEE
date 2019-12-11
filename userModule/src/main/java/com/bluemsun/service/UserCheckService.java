package com.bluemsun.service;

import com.bluemsun.dto.UserDto;
import com.bluemsun.enums.UserStateEnum;

public interface UserCheckService {
    void loginService(String userName, String password);
}
