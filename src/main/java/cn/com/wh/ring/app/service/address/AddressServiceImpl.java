package cn.com.wh.ring.app.service.address;

import cn.com.wh.ring.app.bean.pojo.AddressPojo;
import cn.com.wh.ring.app.bean.pojo.PostPojo;
import cn.com.wh.ring.app.bean.pojo.UserInfo;
import cn.com.wh.ring.app.bean.request.AddressRequest;
import cn.com.wh.ring.app.dao.address.AddressDao;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Hui
 * \* Date: 2017/11/30
 * \* Time: 18:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDao addressDao;

    @Override
    public void bind(@NotNull UserInfo userInfo, AddressRequest addressRequest) {
        if (addressRequest != null) {
            AddressPojo addressPojo = new AddressPojo(addressRequest);
            addressDao.insert(addressPojo);
            userInfo.setAddressId(addressPojo.getId());
            userInfo.setLng(addressRequest.getLng());
            userInfo.setLat(addressRequest.getLat());
        }
    }

    @Override
    public void bind(@NotNull PostPojo postPojo, AddressRequest addressRequest) {
        if (addressRequest != null) {
            AddressPojo addressPojo = new AddressPojo(addressRequest);
            addressDao.insert(addressPojo);
            postPojo.setAddressId(addressPojo.getId());
            postPojo.setLng(addressRequest.getLng());
            postPojo.setLat(addressRequest.getLat());

        }
    }

    @Override
    public String getRegion(Long addressId) {
        AddressPojo addressPojo = addressDao.queryAddressById(addressId);
        return addressPojo == null ? "" : addressPojo.getCity();
    }
}