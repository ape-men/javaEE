package com.bluemsun.dao;

import com.bluemsun.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserDao {
    //注册时调用
    /**
     *检测用户名
     * @param userName
     * @return
     */
    int checkOutUserName(@Param("userName") String userName);

    /**
     * 检测邮箱
     * @param email
     * @return
     */
    int checkOutEmail(@Param("email")String email);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int uploadRegisterInfo(User user);

    //登录时调用

    /**
     * 登录验证
     * @param userName
     * @param password
     * @return
     */
    User CheckLogin(@Param("userName") String userName,@Param("password") String password);

    /**
     * 检查密码是否超过三个月
     * @param userName
     * @param password
     * @return
     */
    Date CheckPasswordTime(@Param("userName") String userName,@Param("password") String password);

    //主页调用
    /**
     * 获得用户所有信息
     * @param userId
     * @return
     */
    User getUserInfo(@Param("userId") int userId);
    //成员管理页

    /**
     * 获得成员总数
     * @return
     */
    int getUserCount();

    /**
     * 列出当前页成员
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<User> listNowUser(@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);

    /**
     * 更新个人身份与审核状态
     * @param userId
     * @param status
     * @param available
     * @return
     */
    int UpdateUserAvailableAndStatus(@Param("userId") int userId,@Param("available") int available,@Param("status") int status);
    //个人主页
    /**
     * 密码更改
     * @param password
     * @param userId
     * @return
     */
    int UpdateUserPassword(@Param("userId") int userId,@Param("password") String password);

    /**
     * 密码验证
     * @param userId
     * @param password
     * @return
     */
    int CheckUserPassword(@Param("userId") int userId,@Param("password") String password);
}
