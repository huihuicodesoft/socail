package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.UserTerminalPojo;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.response.User;
import cn.com.wh.ring.app.constant.UserConstants;
import cn.com.wh.ring.app.dao.user.UserDao;
import cn.com.wh.ring.app.dao.user.UserInfoDao;
import cn.com.wh.ring.app.dao.user.UserSaveIdDao;
import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.pojo.UserSaveIdPojo;
import cn.com.wh.ring.app.dao.user.UserTerminalDao;
import cn.com.wh.ring.app.helper.TokenHelper;
import com.google.common.base.Strings;
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
    UserInfoDao userInfoDao;
    @Autowired
    UserTerminalDao userTerminalDao;
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

    public String createUser(LoginMobile loginMobile) {
        Long result;
        String mobile = loginMobile.getMobile();
        //手机号，三方校验
        Long userId = userDao.queryUserId(mobile, UserConstants.ACCOUNT_TYPE_MOBILE);
        if (userId > 0) {
            //用户已存在
            result = userId;
        } else {
            //添加用户信息
            UserInfoPojo userInfoPojo = new UserInfoPojo();
            userInfoDao.insert(userInfoPojo);

            //创建账号，绑定用户信息
            UserPojo userPojo = new UserPojo();
            userPojo.setAccount(loginMobile.getMobile());
            userPojo.setAccountType(UserConstants.ACCOUNT_TYPE_MOBILE);
            userPojo.setUserInfoId(userInfoPojo.getId());
            userPojo.setUserId(generateUserId());
            userDao.insert(userPojo);

            //记录账号和设备标识
            if (!Strings.isNullOrEmpty(loginMobile.getTerminalMark())){
                UserTerminalPojo userTerminalPojo = new UserTerminalPojo();
                userTerminalPojo.setUserId(userPojo.getUserId());
                userTerminalPojo.setTerminalMark(loginMobile.getTerminalMark());
                userTerminalDao.insert(userTerminalPojo);
            }

            result = userPojo.getUserId();
        }
        return TokenHelper.createUserToken(String.valueOf(result));
    }

    public void updateUserInfo(Long userId, UserInfoPojo userPojo) {

    }

    public User queryUser(Long userId) {
        return null;
    }

    public boolean isValid(Long userId) {
        UserPojo userPojo = userDao.queryByUserId(userId);
        if (userPojo == null){
            return false;
        } else {
            return userPojo.getState() == UserConstants.ACCOUNT_STATE_USEING;
        }
    }
}
