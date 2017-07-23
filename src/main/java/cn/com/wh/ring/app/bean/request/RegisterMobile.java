package cn.com.wh.ring.app.bean.request;


import cn.com.wh.ring.app.utils.PhoneUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by Hui on 2017/7/19.
 */
public class RegisterMobile extends LoginMobile {
    @NotNull(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码不正确")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
