package com.pinyougou.pojo;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@SolrDocument(solrCoreName = "new_core")//JPA写法
@Component
public class CopyItem implements Serializable {

    @Id
    @Field("id")
    private String id;
    @Field("item_title")
    private String title;
    @Field("item_price")
    private Double price;
    @Field("item_image")
    private String image;
    @Field("item_goodsId")
    private Long goodsId;
    @Field("item_category")
    private String category;
    @Field("item_brand")
    private String brand;
    @Field("item_seller")
    private String seller;

    @Field("item_keywords")
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Dynamic
    @Field("item_spec_*")
    private Map<String,String> specMap;
    public Map<String, String> getSpecMap() {
        return specMap;
    }
    public void setSpecMap(Map<String, String> specMap) {
        this.specMap = specMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
