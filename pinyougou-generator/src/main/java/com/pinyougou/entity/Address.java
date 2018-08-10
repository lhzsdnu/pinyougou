package com.pinyougou.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@TableName("tb_address")
public class Address extends Model<Address> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 省
     */
    private String provinceId;
    /**
     * 市
     */
    private String cityId;
    /**
     * 县/区
     */
    private String townId;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 是否是默认 1默认 0否
     */
    private String isDefault;
    /**
     * 备注
     */
    private String notes;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 别名
     */
    private String alias;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Address{" +
        ", id=" + id +
        ", userId=" + userId +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", townId=" + townId +
        ", mobile=" + mobile +
        ", address=" + address +
        ", contact=" + contact +
        ", isDefault=" + isDefault +
        ", notes=" + notes +
        ", createDate=" + createDate +
        ", alias=" + alias +
        "}";
    }
}
