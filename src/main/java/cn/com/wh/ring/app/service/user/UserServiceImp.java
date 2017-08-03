package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.*;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.request.RegisterMobile;
import cn.com.wh.ring.app.bean.request.ThirdAccount;
import cn.com.wh.ring.app.constant.UserConstants;
import cn.com.wh.ring.app.dao.user.UserDao;
import cn.com.wh.ring.app.dao.user.UserInfoDao;
import cn.com.wh.ring.app.dao.user.UserSaveIdDao;
import cn.com.wh.ring.app.dao.user.UserTerminalDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.SmsCodeHelper;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import cn.com.wh.ring.common.secret.RSA;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Hui on 2017/6/14.
 */
@Service
@Transactional
public class UserServiceImp implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class.getName());
    private static final long baseUserIdNumber = 100000;

    @Autowired
    UserDao userDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    UserTerminalDao userTerminalDao;
    @Autowired
    UserSaveIdDao userSaveIdDao;
    @Autowired
    SmsCodeHelper smsCodeHelper;

    private List<UserSaveId> mUserSaveIdList;

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
            for (UserSaveId userSaveId : mUserSaveIdList) {
                if (userId == userSaveId.getSaveUserId()) {
                    //是保留ID
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public String registerMobileUser(@Valid RegisterMobile registerMobile) {
        String result = "";
        if (smsCodeHelper.verification(registerMobile)) {
            //手机号校验
            User user = userDao.queryByAccount(registerMobile.getMobile(), UserConstants.ACCOUNT_TYPE_MOBILE);
            if (user == null) {
                //添加用户信息
                UserInfo userInfo = new UserInfo();
                userInfoDao.insert(userInfo);

                try {
                    //创建账号，绑定用户信息
                    user = new User();
                    user.setAccount(registerMobile.getMobile());
                    user.setPassword(RSA.decrypt(registerMobile.getPassword()));
                    user.setAccountType(UserConstants.ACCOUNT_TYPE_MOBILE);
                    user.setUserInfoId(userInfo.getId());
                    user.setUserId(generateUserId());
                    userDao.insert(user);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
                }
                //记录账号和设备标识
                recordUserTerminal(TokenHelper.getCurrentMark(), user.getUserId());

                result = TokenHelper.createUserToken(String.valueOf(user.getUserId()));
            } else {
                //用户已存在
                throw new ServiceException(ReturnCode.ERROR_MOBILE_EXIST, "error_mobile_exist");
            }
        }
        return result;
    }

    @Override
    public String loginMobileUser(LoginMobile mobileAccount) {
        User user = getMobileAccountUsing(mobileAccount.getMobile());
        if (user != null) {
            String realPassword;
            try {
                realPassword = RSA.decrypt(mobileAccount.getPassword());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
            }
            if (!Strings.isNullOrEmpty(realPassword) && realPassword.equals(user.getPassword())) {
                return TokenHelper.createUserToken(String.valueOf(user.getUserId()));
            } else {
                throw new ServiceException(ReturnCode.ERROR_ACCOUNT_PASSWORD, "error_account_password");
            }

        }
        return null;
    }

    public void updatePassword(@Valid RegisterMobile mobileAccount) {
        if (smsCodeHelper.verification(mobileAccount)) {
            //手机号校验
            User user = getMobileAccountUsing(mobileAccount.getMobile());
            if (user != null) {
                try {
                    userDao.updatePassword(mobileAccount.getMobile(), UserConstants.ACCOUNT_TYPE_MOBILE,
                            RSA.decrypt(mobileAccount.getPassword()));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
                }
            }
        }
    }

    @Override
    public User validUserMobile(String mobile) {
        return getMobileAccountUsing(mobile);
    }

    private User getMobileAccountUsing(String mobile) {
        User user = userDao.queryByAccount(mobile, UserConstants.ACCOUNT_TYPE_MOBILE);
        if (user == null) {
            throw new ServiceException(ReturnCode.ERROR_MOBILE_UN_EXIST, "error_mobile_un_exist");
        } else {
            if (user.getState() == UserConstants.ACCOUNT_STATE_USING) {
                return user;
            } else {
                throw new ServiceException(ReturnCode.ERROR_ACCOUNT_UN_USE, "error_account_un_use");
            }
        }
    }

    public String loginThirdUser(ThirdAccount thirdAccount) {
        //三方校验
        User user = userDao.queryByAccount(thirdAccount.getAccount(), thirdAccount.getAccountType());
        if (user == null) {
            //添加用户信息
            UserInfo userInfo = new UserInfo();
            userInfoDao.insert(userInfo);

            //创建账号，绑定用户信息
            user = new User();
            user.setAccount(thirdAccount.getAccount());
            user.setAccountType(thirdAccount.getAccountType());
            user.setAccessToken(thirdAccount.getAccessToken());
            user.setRefreshToken(thirdAccount.getRefreshToken());
            user.setUserInfoId(userInfo.getId());
            user.setUserId(generateUserId());
            userDao.insert(user);

            //记录账号和设备标识
            recordUserTerminal(TokenHelper.getCurrentMark(), user.getUserId());
        } else {
            user.setAccessToken(thirdAccount.getAccessToken());
            user.setRefreshToken(thirdAccount.getRefreshToken());
            userDao.updateToken(user);
        }
        return TokenHelper.createUserToken(String.valueOf(user.getUserId()));
    }

    private void recordUserTerminal(String terminalMark, Long userId) {
        if (!Strings.isNullOrEmpty(terminalMark)) {
            UserTerminal userTerminal = new UserTerminal();
            userTerminal.setUserId(userId);
            userTerminal.setTerminalMark(terminalMark);
            userTerminalDao.insert(userTerminal);
        }
    }

    public void updateUserInfo(Long userId, UserInfo userPojo) {

    }

    public cn.com.wh.ring.app.bean.response.User queryUser(Long userId) {
        return null;
    }

    public boolean isValid(Long userId) {
        User user = userDao.queryByUserId(userId);
        if (user == null) {
            return false;
        } else {
            return user.getState() == UserConstants.ACCOUNT_STATE_USING;
        }
    }
}
