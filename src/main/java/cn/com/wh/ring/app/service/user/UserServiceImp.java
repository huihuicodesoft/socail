package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.dao.user.UserDao;
import cn.com.wh.ring.app.dao.user.UserSaveIdDao;
import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.pojo.UserSaveIdPojo;
import cn.com.wh.ring.app.bean.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Hui on 2017/6/14.
 */
@Service
@Transactional
public class UserServiceImp implements UserService {
    private static final long baseUserIdNumber = 100000;

    @Autowired
    UserDao userDao;

    @Autowired
    UserSaveIdDao userSaveIdDao;

    private List<UserSaveIdPojo> mUserSaveIdList;

    @PostConstruct
    private void init() {
        mUserSaveIdList = userSaveIdDao.getAll();
    }

    private Long generateUserId() {
        Long maxUserId = userDao.queryMaxUserId();
        if (maxUserId == null) {
            maxUserId = baseUserIdNumber;
        } else {
            maxUserId++;
        }
        while (isSaveUserId(maxUserId)) {
            maxUserId++;
        }
        return maxUserId;
    }

    private boolean isSaveUserId(Long userId) {
        if (mUserSaveIdList != null) {
            for (UserSaveIdPojo userSaveId : mUserSaveIdList) {
                if (userId == userSaveId.getSaveUserId()) {
                    //是保留ID
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Long createUser(UserPojo userPojo) {
        //手机号，三方校验
        Long userId = userDao.queryUserId(userPojo.getAccount(), userPojo.getAccountType());
        if (userId > 0) {
            //用户已存在
            userPojo.setUserId(userId);
        } else {
            userPojo.setUserId(generateUserId());
            userDao.insert(userPojo);
        }
        return userPojo.getUserId();
    }

    public void updateUserInfo(Long userId, UserInfoPojo userPojo) {

    }

    public UserVo queryUser(Long userId) {
        return null;
    }
}
