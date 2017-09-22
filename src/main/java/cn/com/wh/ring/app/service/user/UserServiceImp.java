package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.*;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.request.RegisterMobile;
import cn.com.wh.ring.app.bean.request.LoginThird;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.constant.RoleConstants;
import cn.com.wh.ring.app.dao.user.*;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.SmsCodeHelper;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.helper.UserHelper;
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
    UserTerminalService userTerminalService;
    @Autowired
    UserSaveIdService userSaveIdService;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    TerminalService terminalService;
    @Autowired
    SmsCodeHelper smsCodeHelper;

    private List<UserSaveId> mUserSaveIdList;

    @PostConstruct
    private void init() {
        mUserSaveIdList = userSaveIdService.getAll();
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

    public void registerMobileUser(@Valid RegisterMobile registerMobile) {
        if (smsCodeHelper.verification(registerMobile)) {
            //手机号校验
            User user = userDao.queryByAccount(registerMobile.getMobile(), User.ACCOUNT_TYPE_MOBILE);
            if (user == null) {
                //添加用户信息
                UserInfo userInfo = new UserInfo();
                userInfoDao.insert(userInfo);

                try {
                    //创建账号，绑定用户信息
                    user = new User();
                    user.setAccount(registerMobile.getMobile());
                    user.setPassword(RSA.decrypt(registerMobile.getPassword()));
                    user.setAccountType(User.ACCOUNT_TYPE_MOBILE);
                    user.setUserInfoId(userInfo.getId());
                    user.setUserId(generateUserId());
                    userDao.insert(user);

                    userRoleDao.insert(user.getUserId(), RoleConstants.ROLE_USER);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
                }
            } else {
                //用户已存在
                throw new ServiceException(ReturnCode.ERROR_MOBILE_EXIST, "error_mobile_exist");
            }
        }
    }

    @Override
    public String loginMobileUser(LoginMobile loginMobile) {
        if (TokenHelper.getCurrentSubjectType() != Constants.USER_TYPE_TERMINAL) {
            throw new ServiceException(ReturnCode.ERROR_TOKEN_ERROR, "error_token_error");
        }
        User user = getMobileAccountUsing(loginMobile.getMobile());
        if (user != null) {
            String realPassword;
            try {
                realPassword = RSA.decrypt(loginMobile.getPassword());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
            }
            if (!Strings.isNullOrEmpty(realPassword) && realPassword.equals(user.getPassword())) {
                //补充设备详细信息,
                terminalService.recordDetailInfo(loginMobile.getTerminalDetailInfo());
                //绑定用户和设备
                Terminal terminal = terminalService.queryByMark(TokenHelper.getCurrentSubjectMark());
                bindUserTerminal(user.getUserId(), terminal.getId());
                return TokenHelper.createToken(user.getUserId(), terminal.getId());
            } else {
                throw new ServiceException(ReturnCode.ERROR_ACCOUNT_PASSWORD, "error_account_password");
            }

        }
        return null;
    }

    public void updatePassword(@Valid RegisterMobile registerMobile) {
        if (smsCodeHelper.verification(registerMobile)) {
            //手机号校验
            User user = getMobileAccountUsing(registerMobile.getMobile());
            if (user != null) {
                try {
                    userDao.updatePassword(registerMobile.getMobile(), User.ACCOUNT_TYPE_MOBILE,
                            RSA.decrypt(registerMobile.getPassword()));
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
        User user = userDao.queryByAccount(mobile, User.ACCOUNT_TYPE_MOBILE);
        if (user == null) {
            throw new ServiceException(ReturnCode.ERROR_MOBILE_UN_EXIST, "error_mobile_un_exist");
        } else {
            if (UserHelper.canUse(user.getState())) {
                return user;
            } else {
                throw new ServiceException(ReturnCode.ERROR_ACCOUNT_UN_USE, "error_account_un_use");
            }
        }
    }

    public String loginThirdUser(LoginThird loginThird) {
        if (TokenHelper.getCurrentSubjectType() != Constants.USER_TYPE_TERMINAL) {
            throw new ServiceException(ReturnCode.ERROR_TOKEN_ERROR, "error_token_error");
        }
        //三方校验
        User user = userDao.queryByAccount(loginThird.getAccount(), loginThird.getAccountType());
        if (user == null) {
            //添加用户信息
            UserInfo userInfo = new UserInfo();
            userInfoDao.insert(userInfo);

            //创建账号，绑定用户信息
            user = new User();
            user.setAccount(loginThird.getAccount());
            user.setAccountType(loginThird.getAccountType());
            user.setAccessToken(loginThird.getAccessToken());
            user.setRefreshToken(loginThird.getRefreshToken());
            user.setUserInfoId(userInfo.getId());
            user.setUserId(generateUserId());
            userDao.insert(user);

            userRoleDao.insert(user.getUserId(), RoleConstants.ROLE_USER);
        } else {
            if (!UserHelper.canUse(user.getState())) {
                throw new ServiceException(ReturnCode.ERROR_ACCOUNT_UN_USE, "error_account_un_use");
            }
            user.setAccessToken(loginThird.getAccessToken());
            user.setRefreshToken(loginThird.getRefreshToken());
            userDao.updateToken(user);
        }

        //补充设备详细信息,
        terminalService.recordDetailInfo(loginThird.getTerminalDetailInfo());
        //绑定用户和设备
        Terminal terminal = terminalService.queryByMark(TokenHelper.getCurrentSubjectMark());
        bindUserTerminal(user.getUserId(), terminal.getId());

        return TokenHelper.createToken(user.getUserId(), terminal.getId());
    }

    private void bindUserTerminal(Long userId, Long terminalId) {
        UserTerminal userTerminal = new UserTerminal();
        userTerminal.setUserId(userId);
        userTerminal.setTerminalId(terminalId);
        userTerminal.setUsing(Constants.BOOLEAN_TRUE);
        userTerminalService.bindUserTerminal(userTerminal);
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
            return UserHelper.canUse(user.getState());
        }
    }
}
