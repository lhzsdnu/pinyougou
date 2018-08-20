package com.pinyougou.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@TableName("tb_goods_desc")
public class GoodsDesc extends Model<GoodsDesc> {

    private static final long serialVersionUID = 1L;

    /**
     * SPU_ID
     */
    @TableId(value = "goods_id", type = IdType.INPUT)
    private Long goodsId;
    /**
     * 描述
     */
    private String introduction;
    /**
     * 规格结果集，所有规格，包含isSelected
     */
    private String specificationItems;
    /**
     * 自定义属性（参数结果）
     */
    private String customAttributeItems;
    private String itemImages;
    /**
     * 包装列表
     */
    private String packageList;
    /**
     * 售后服务
     */
    private String saleService;


    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSpecificationItems() {
        return specificationItems;
    }

    public void setSpecificationItems(String specificationItems) {
        this.specificationItems = specificationItems;
    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems;
    }

    public String getItemImages() {
        return itemImages;
    }

    public void setItemImages(String itemImages) {
        this.itemImages = itemImages;
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList;
    }

    public String getSaleService() {
        return saleService;
    }

    public void setSaleService(String saleService) {
        this.saleService = saleService;
    }

    @Override
    protected Serializable pkVal() {
        return this.goodsId;
    }


}
