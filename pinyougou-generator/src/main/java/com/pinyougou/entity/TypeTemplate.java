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
 * 
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@TableName("tb_type_template")
public class TypeTemplate extends Model<TypeTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 关联规格
     */
    private String specIds;
    /**
     * 关联品牌
     */
    private String brandIds;
    /**
     * 自定义属性
     */
    private String customAttributeItems;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TypeTemplate{" +
        ", id=" + id +
        ", name=" + name +
        ", specIds=" + specIds +
        ", brandIds=" + brandIds +
        ", customAttributeItems=" + customAttributeItems +
        "}";
    }
}
