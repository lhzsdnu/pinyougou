package com.pinyougou.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 行政区域县区信息表
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@TableName("tb_areas")
public class Areas extends Model<Areas> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 区域ID
     */
    private String areaid;
    /**
     * 区域名称
     */
    private String area;
    /**
     * 城市ID
     */
    private String cityid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Areas{" +
        ", id=" + id +
        ", areaid=" + areaid +
        ", area=" + area +
        ", cityid=" + cityid +
        "}";
    }
}
