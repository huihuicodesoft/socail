package cn.com.wh.ring.app.bean.response;


/**
 * Created by Hui on 2017/9/22.
 */
public class LoginUserResponse {
    private String token;
    private UserInfoResponse userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoResponse getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoResponse userInfo) {
        this.userInfo = userInfo;
    }
}
