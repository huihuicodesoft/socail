package cn.com.wh.ring.app.bean.request;

/**
 * Created by Hui on 2017/7/19.
 */
public class LoginMobile {
    private String mobile;
    private String code;
    private String terminalMark;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTerminalMark() {
        return terminalMark;
    }

    public void setTerminalMark(String terminalMark) {
        this.terminalMark = terminalMark;
    }
}
