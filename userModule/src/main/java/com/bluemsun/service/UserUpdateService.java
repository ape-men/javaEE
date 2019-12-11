package com.bluemsun.service;

import com.bluemsun.entity.User;
import com.bluemsun.enums.UserStateEnum;

import java.util.List;

public interface UserUpdateService {
    UserStateEnum updateUser();
    Integer updatePassword();
    Integer findPassword();
    Integer submitRegister();
}
