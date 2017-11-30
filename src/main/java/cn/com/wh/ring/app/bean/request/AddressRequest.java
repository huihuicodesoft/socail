package cn.com.wh.ring.app.bean.request;

/**
 * \* Created with IntelliJ IDEA.
 * \* UserPojo: Hui
 * \* Date: 2017/11/30
 * \* Time: 12:04
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class AddressRequest {
    private String country;
    private String province;
    private String city;
    private String district;
    private Double lng;
    private Double lat;

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

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}