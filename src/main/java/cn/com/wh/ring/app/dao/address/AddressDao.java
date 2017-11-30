package cn.com.wh.ring.app.dao.address;

import cn.com.wh.ring.app.bean.pojo.AddressPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/11/30.
 */
@Repository
public interface AddressDao {
    void insert(AddressPojo addressPojo);

    AddressPojo queryAddressById(@Param("id") Long id);
}
