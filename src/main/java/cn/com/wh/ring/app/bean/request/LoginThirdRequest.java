package cn.com.wh.ring.app.bean.request;

import cn.com.wh.ring.app.bean.pojo.AddressPojo;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by Hui on 2017/7/23.
 */
public class LoginThirdRequest {
    @NotNull(message = "账号不能为空")
    private String account;

    @Range(min = UserPojo.ACCOUNT_TYPE_WX, max = UserPojo.ACCOUNT_TYPE_SINA, message = "账号类型非法")
    private byte accountType;

    @NotNull(message = "accessToken不能为空")
    private String accessToken;

    private String refreshToken;

    private TerminalDetailInfoRequest terminalDetailInfoRequest;

    private AddressRequest address;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public byte getAccountType() {
        return accountType;
    }

    public void setAccountType(byte accountType) {
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

    public TerminalDetailInfoRequest getTerminalDetailInfoRequest() {
        return terminalDetailInfoRequest;
    }

    public void setTerminalDetailInfoRequest(TerminalDetailInfoRequest terminalDetailInfoRequest) {
        this.terminalDetailInfoRequest = terminalDetailInfoRequest;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }
}
