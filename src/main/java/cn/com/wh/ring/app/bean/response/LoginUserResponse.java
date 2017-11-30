package cn.com.wh.ring.app.bean.response;


/**
 * Created by Hui on 2017/9/22.
 */
public class LoginUserResponse {
    private String token;
    private UserInfoResponse userInfoResponse;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoResponse getUserInfoResponse() {
        return userInfoResponse;
    }

    public void setUserInfoResponse(UserInfoResponse userInfoResponse) {
        this.userInfoResponse = userInfoResponse;
    }
}
