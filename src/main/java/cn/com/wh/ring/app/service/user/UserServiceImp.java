package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.*;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.request.RegisterMobile;
import cn.com.wh.ring.app.bean.request.ThirdAccount;
import cn.com.wh.ring.app.bean.response.User;
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

    public String registerMobileUser(@Valid RegisterMobile registerMobile) {
        String result = "";
        if (smsCodeHelper.verification(registerMobile)) {
            //手机号校验
            UserPojo userPojo = userDao.queryByAccount(registerMobile.getMobile(), UserConstants.ACCOUNT_TYPE_MOBILE);
            if (userPojo == null) {
                //添加用户信息
                UserInfoPojo userInfoPojo = new UserInfoPojo();
                userInfoDao.insert(userInfoPojo);

                try {
                    //创建账号，绑定用户信息
                    userPojo = new UserPojo();
                    userPojo.setAccount(registerMobile.getMobile());
                    userPojo.setPassword(RSA.decrypt(registerMobile.getPassword()));
                    userPojo.setAccountType(UserConstants.ACCOUNT_TYPE_MOBILE);
                    userPojo.setUserInfoId(userInfoPojo.getId());
                    userPojo.setUserId(generateUserId());
                    userDao.insert(userPojo);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
                }
                //记录账号和设备标识
                recordUserTerminal(TokenHelper.getCurrentMark(), userPojo.getUserId());

                result = TokenHelper.createUserToken(String.valueOf(userPojo.getUserId()));
            } else {
                //用户已存在
                throw new ServiceException(ReturnCode.ERROR_MOBILE_EXIST, "error_mobile_exist");
            }
        }
        return result;
    }

    @Override
    public String loginMobileUser(LoginMobile mobileAccount) {
        UserPojo userPojo = getMobileAccountUsing(mobileAccount.getMobile());
        if (userPojo != null) {
            try {
                String realPassword = RSA.decrypt(mobileAccount.getPassword());
                if (realPassword.equals(userPojo.getPassword())) {
                    return TokenHelper.createUserToken(String.valueOf(userPojo.getUserId()));
                } else {
                    throw new ServiceException(ReturnCode.ERROR_ACCOUNT_PASSWORD, "error_account_password");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
            }
        }
        return null;
    }

    public void updatePassword(@Valid RegisterMobile mobileAccount) {
        if (smsCodeHelper.verification(mobileAccount)) {
            //手机号校验
            UserPojo userPojo = getMobileAccountUsing(mobileAccount.getMobile());
            if (userPojo != null) {
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
    public UserPojo validUserMobile(String mobile) {
        return getMobileAccountUsing(mobile);
    }

    private UserPojo getMobileAccountUsing(String mobile) {
        UserPojo userPojo = userDao.queryByAccount(mobile, UserConstants.ACCOUNT_TYPE_MOBILE);
        if (userPojo == null) {
            throw new ServiceException(ReturnCode.ERROR_MOBILE_UN_EXIST, "error_mobile_un_exist");
        } else {
            if (userPojo.getState() == UserConstants.ACCOUNT_STATE_USING) {
                return userPojo;
            } else {
                throw new ServiceException(ReturnCode.ERROR_ACCOUNT_UN_USE, "error_account_un_use");
            }
        }
    }

    public String loginThirdUser(ThirdAccount thirdAccount) {
        //三方校验
        UserPojo userPojo = userDao.queryByAccount(thirdAccount.getAccount(), thirdAccount.getAccountType());
        if (userPojo == null) {
            //添加用户信息
            UserInfoPojo userInfoPojo = new UserInfoPojo();
            userInfoDao.insert(userInfoPojo);

            //创建账号，绑定用户信息
            userPojo = new UserPojo();
            userPojo.setAccount(thirdAccount.getAccount());
            userPojo.setAccountType(thirdAccount.getAccountType());
            userPojo.setAccessToken(thirdAccount.getAccessToken());
            userPojo.setRefreshToken(thirdAccount.getRefreshToken());
            userPojo.setUserInfoId(userInfoPojo.getId());
            userPojo.setUserId(generateUserId());
            userDao.insert(userPojo);

            //记录账号和设备标识
            recordUserTerminal(TokenHelper.getCurrentMark(), userPojo.getUserId());
        } else {
            userPojo.setAccessToken(thirdAccount.getAccessToken());
            userPojo.setRefreshToken(thirdAccount.getRefreshToken());
            userDao.updateToken(userPojo);
        }
        return TokenHelper.createUserToken(String.valueOf(userPojo.getUserId()));
    }

    private void recordUserTerminal(String terminalMark, Long userId) {
        if (!Strings.isNullOrEmpty(terminalMark)) {
            UserTerminalPojo userTerminalPojo = new UserTerminalPojo();
            userTerminalPojo.setUserId(userId);
            userTerminalPojo.setTerminalMark(terminalMark);
            userTerminalDao.insert(userTerminalPojo);
        }
    }

    public void updateUserInfo(Long userId, UserInfoPojo userPojo) {

    }

    public User queryUser(Long userId) {
        return null;
    }

    public boolean isValid(Long userId) {
        UserPojo userPojo = userDao.queryByUserId(userId);
        if (userPojo == null) {
            return false;
        } else {
            return userPojo.getState() == UserConstants.ACCOUNT_STATE_USING;
        }
    }
}
