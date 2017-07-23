package cn.com.wh.ring.app.bean.request;

import cn.com.wh.ring.app.constant.UserConstants;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by Hui on 2017/7/23.
 */
public class ThirdAccount {
    @NotNull(message = "账号不能为空")
    private String account;

    @Range(min = UserConstants.ACCOUNT_STATE_WX, max = UserConstants.ACCOUNT_STATE_SINA, message = "账号类型非法")
    private int accountType;

    @NotNull(message = "accessToken不能为空")
    private String accessToken;

    private String refreshToken;

    private String terminalMark;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTerminalMark() {
        return terminalMark;
    }

    public void setTerminalMark(String terminalMark) {
        this.terminalMark = terminalMark;
    }
}
