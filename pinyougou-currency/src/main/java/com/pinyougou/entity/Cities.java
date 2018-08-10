package com.pinyougou.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 行政区域地州市信息表
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@TableName("tb_cities")
public class Cities extends Model<Cities> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 城市ID
     */
    private String cityid;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 省份ID
     */
    private String provinceid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Cities{" +
        ", id=" + id +
        ", cityid=" + cityid +
        ", city=" + city +
        ", provinceid=" + provinceid +
        "}";
    }
}
