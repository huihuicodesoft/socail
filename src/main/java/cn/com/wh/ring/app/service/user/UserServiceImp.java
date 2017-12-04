package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.*;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
import cn.com.wh.ring.app.bean.request.*;
import cn.com.wh.ring.app.bean.response.*;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.constant.RoleConstants;
import cn.com.wh.ring.app.dao.user.*;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.FileHelper;
import cn.com.wh.ring.app.helper.SmsCodeHelper;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.helper.AccountHelper;
import cn.com.wh.ring.app.service.address.AddressService;
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
    AddressService addressService;
    @Autowired
    UserTerminalService userTerminalService;
    @Autowired
    UserSaveIdDao userSaveIdDao;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    TerminalService terminalService;
    @Autowired
    SmsCodeHelper smsCodeHelper;
    @Autowired
    FileHelper fileHelper;

    private List<UserSaveIdPojo> mUserSaveIdPojoList;

    @PostConstruct
    private void init() {
        mUserSaveIdPojoList = userSaveIdDao.getAll();
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
        if (mUserSaveIdPojoList != null) {
            for (UserSaveIdPojo userSaveIdPojo : mUserSaveIdPojoList) {
                if (userId == userSaveIdPojo.getSaveUserId()) {
                    //是保留ID
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void registerMobileUser(@Valid RegisterMobileRequest registerMobileRequest) {
        if (smsCodeHelper.verification(registerMobileRequest)) {
            //手机号校验
            UserPojo userPojo = userDao.queryByAccount(registerMobileRequest.getMobile(), UserPojo.ACCOUNT_TYPE_MOBILE);
            if (userPojo == null) {
                //添加用户信息
                UserInfoPojo userInfoPojo = getUserInfoByAddress(registerMobileRequest.getAddress());

                try {
                    //创建账号，绑定用户信息
                    userPojo = new UserPojo();
                    userPojo.setAccount(registerMobileRequest.getMobile());
                    userPojo.setPassword(RSA.decrypt(registerMobileRequest.getPassword()));
                    userPojo.setAccountType(UserPojo.ACCOUNT_TYPE_MOBILE);
                    userPojo.setUserInfoId(userInfoPojo.getId());
                    userPojo.setUserId(generateUserId());
                    userDao.insert(userPojo);

                    userRoleDao.insert(userPojo.getUserId(), RoleConstants.ROLE_USER);
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
    public LoginUserResponse loginMobileUser(LoginMobileRequest loginMobileRequest) {
        if (TokenHelper.getCurrentSubjectType() != Constants.USER_TYPE_TERMINAL) {
            throw new ServiceException(ReturnCode.ERROR_TOKEN_ERROR, "error_token_error");
        }
        UserPojo userPojo = getMobileAccountUsing(loginMobileRequest.getMobile());
        if (userPojo != null) {
            String realPassword;
            try {
                realPassword = RSA.decrypt(loginMobileRequest.getPassword());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ServiceException(ReturnCode.ERROR_PROGRAM, "error_program");
            }
            if (!Strings.isNullOrEmpty(realPassword) && realPassword.equals(userPojo.getPassword())) {
                return recordTerminalToResponse(loginMobileRequest.getTerminalDetailInfoRequest(), userPojo);
            } else {
                throw new ServiceException(ReturnCode.ERROR_ACCOUNT_PASSWORD, "error_account_password");
            }

        }
        return null;
    }

    @Override
    public LoginUserResponse loginThirdUser(LoginThirdRequest loginThirdRequest) {
        if (TokenHelper.getCurrentSubjectType() != Constants.USER_TYPE_TERMINAL) {
            throw new ServiceException(ReturnCode.ERROR_TOKEN_ERROR, "error_token_error");
        }
        //三方校验
        UserPojo userPojo = userDao.queryByAccount(loginThirdRequest.getAccount(), loginThirdRequest.getAccountType());
        if (userPojo == null) {
            //添加用户信息
            UserInfoPojo userInfoPojo = getUserInfoByAddress(loginThirdRequest.getAddress());

            //创建账号，绑定用户信息
            userPojo = new UserPojo();
            userPojo.setAccount(loginThirdRequest.getAccount());
            userPojo.setAccountType(loginThirdRequest.getAccountType());
            userPojo.setAccessToken(loginThirdRequest.getAccessToken());
            userPojo.setRefreshToken(loginThirdRequest.getRefreshToken());
            userPojo.setUserInfoId(userInfoPojo.getId());
            userPojo.setUserId(generateUserId());
            userDao.insert(userPojo);

            userRoleDao.insert(userPojo.getUserId(), RoleConstants.ROLE_USER);
        } else {
            if (!AccountHelper.canUse(userPojo.getState())) {
                throw new ServiceException(ReturnCode.ERROR_USER_LOCKED, "error_account_locked");
            }
            userPojo.setAccessToken(loginThirdRequest.getAccessToken());
            userPojo.setRefreshToken(loginThirdRequest.getRefreshToken());
            userDao.updateToken(userPojo);
        }

        return recordTerminalToResponse(loginThirdRequest.getTerminalDetailInfoRequest(), userPojo);
    }

    private UserInfoPojo getUserInfoByAddress(AddressRequest addressRequest) {
        //添加用户信息
        UserInfoPojo userInfoPojo = new UserInfoPojo();
        //添加注册地址信息
        addressService.bind(userInfoPojo, addressRequest);
        userInfoDao.insert(userInfoPojo);
        return userInfoPojo;
    }

    @Override
    public void updatePassword(@Valid RegisterMobileRequest registerMobileRequest) {
        if (smsCodeHelper.verification(registerMobileRequest)) {
            //手机号校验
            UserPojo userPojo = getMobileAccountUsing(registerMobileRequest.getMobile());
            if (userPojo != null) {
                try {
                    userDao.updatePassword(registerMobileRequest.getMobile(), UserPojo.ACCOUNT_TYPE_MOBILE,
                            RSA.decrypt(registerMobileRequest.getPassword()));
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
        UserPojo userPojo = userDao.queryByAccount(mobile, UserPojo.ACCOUNT_TYPE_MOBILE);
        if (userPojo == null) {
            throw new ServiceException(ReturnCode.ERROR_USER_UN_EXIST, "error_mobile_un_exist");
        } else {
            if (AccountHelper.canUse(userPojo.getState())) {
                return userPojo;
            } else {
                throw new ServiceException(ReturnCode.ERROR_USER_LOCKED, "error_account_locked");
            }
        }
    }

    private UserPojo getUserUsing(Long userId) {
        UserPojo userPojo = userDao.queryByUserId(userId);
        if (userPojo == null) {
            throw new ServiceException(ReturnCode.ERROR_USER_UN_EXIST, "error_user_un_exist");
        } else {
            if (AccountHelper.canUse(userPojo.getState())) {
                return userPojo;
            } else {
                throw new ServiceException(ReturnCode.ERROR_USER_LOCKED, "error_user_locked");
            }
        }
    }

    /**
     * 记录设备信息，组合登陆成功结果
     *
     * @param terminalDetailInfoRequest
     * @param userPojo
     * @return
     */
    private LoginUserResponse recordTerminalToResponse(TerminalDetailInfoRequest terminalDetailInfoRequest, UserPojo userPojo) {
        //补充设备详细信息,
        terminalService.recordTerminalDetailInfo(terminalDetailInfoRequest);
        //绑定用户和设备
        TerminalPojo terminalPojo = terminalService.queryByUuid(TokenHelper.getCurrentSubjectUuid());
        bindUserTerminal(userPojo.getUserId(), terminalPojo.getId());

        String token = TokenHelper.createToken(userPojo.getUserId(), terminalPojo.getId());
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setToken(token);
        loginUserResponse.setUserInfo(queryUser(userPojo.getUserId()));
        return loginUserResponse;
    }

    private void bindUserTerminal(Long userId, Long terminalId) {
        UserTerminalPojo userTerminalPojo = new UserTerminalPojo();
        userTerminalPojo.setUserId(userId);
        userTerminalPojo.setTerminalId(terminalId);
        userTerminalPojo.setUsing(Constants.BOOLEAN_TRUE);
        userTerminalService.bindUserTerminal(userTerminalPojo);
    }

    public void updateUserInfo(UserInfoPojo userInfoPojo) {
        UserPojo userPojo = getUserUsing(TokenHelper.getCurrentSubjectUserId());
        userInfoPojo.setId(userPojo.getUserInfoId());
        userInfoDao.update(userInfoPojo);
    }

    public UserInfoResponse queryUser(Long userId) {
        UserPojo userPojo = getUserUsing(userId);
        UserInfoPojo userInfoPojo = userInfoDao.queryById(userPojo.getUserInfoId());
        //拼接头像地址
        String avatar = userInfoPojo.getAvatar();
        if (!Strings.isNullOrEmpty(avatar))
            userInfoPojo.setAvatar(fileHelper.getFileAvatarUrl(avatar));
        String region = addressService.getRegion(userInfoPojo.getAddressId());
        return new UserInfoResponse(userId, userInfoPojo, region);
    }

    public boolean isValid(Long userId) throws ServiceException{
        UserPojo userPojo = userDao.queryByUserId(userId);
        if(userPojo == null) {
            throw ServiceException.create(ReturnCode.ERROR_USER_UN_EXIST, "error_user_un_exist");
        } else {
            return AccountHelper.canUse(userPojo.getState());
        }
    }
}
