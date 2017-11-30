package cn.com.wh.ring.app.service.address;

import cn.com.wh.ring.app.bean.pojo.PostPojo;
import cn.com.wh.ring.app.bean.pojo.UserInfo;
import cn.com.wh.ring.app.bean.request.AddressRequest;

/**
 * Created by Hui on 2017/11/30.
 */
public interface AddressService {
    void bind(UserInfo userInfo, AddressRequest addressRequest);

    void bind(PostPojo postPojo, AddressRequest addressRequest);

    String getRegion(Long addressId);
}
