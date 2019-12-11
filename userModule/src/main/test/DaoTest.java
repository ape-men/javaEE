import com.bluemsun.dao.UserDao;
import com.bluemsun.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DaoTest extends BaseTest {
@Autowired
private UserDao userDao;
@Test
public void testUserDao(){
  //List<User> userList = userDao.listNowUser(0,15);
//    for(User user : userList){
//        System.out.println(user.getUserName());
//    User user;
//    user=userDao.getUserInfo(1);
//    System.out.println(user.getUserName());
//    List<Copy> copyList = copyDao.listOneHomeworkAllCopy(3);
//    for (Copy c: copyList){
//        System.out.println(c.getUserId());
//        User user = userDao.getUserInfo(c.getUserId());
//        System.out.println(user.getUserName());
//        System.out.println(c.getBelongHomework().getHomeworkName());
//    }
//    List<Homework> homeworkList = homeworkDao.listNowHomework(0,15,null);
//    for (Homework h:homeworkList){
//        System.out.println(h.getHomeworkName());
//    }
}}
