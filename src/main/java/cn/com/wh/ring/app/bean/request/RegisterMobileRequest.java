package cn.com.wh.ring.app.bean.request;

import cn.com.wh.ring.app.utils.PhoneUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Hui on 2017/7/19.
 */
public class RegisterMobileRequest {
    @NotNull(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号非法")
    @Pattern(regexp = PhoneUtils.PHONE_REGEX, message = "手机号非法")
    private String mobile;

    @NotNull(message = "密码不能为空")
    @Length(min = 1, message = "密码不能为空")
    private String password;

    @NotNull(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码不正确")
    private String code;

    private AddressRequest address;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }
}
