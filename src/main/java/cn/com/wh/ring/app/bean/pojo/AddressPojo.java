package cn.com.wh.ring.app.bean.pojo;

import cn.com.wh.ring.app.bean.request.AddressRequest;
import org.apache.ibatis.type.Alias;

/**
 * \* Created with IntelliJ IDEA.
 * \* UserPojo: Hui
 * \* Date: 2017/11/30
 * \* Time: 11:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Alias("AddressPojo")
public class AddressPojo {
    private long id;
    private String country;
    private String province;
    private String city;
    private String district;

    public AddressPojo() {
    }

    public AddressPojo(AddressRequest addressRequest) {
        this.country = addressRequest.getCountry();
        this.province = addressRequest.getProvince();
        this.city = addressRequest.getCity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}