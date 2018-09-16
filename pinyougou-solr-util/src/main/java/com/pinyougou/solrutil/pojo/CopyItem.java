package com.pinyougou.solrutil.pojo;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.stereotype.Component;

import java.util.Map;

@SolrDocument(solrCoreName = "new_core")//JPA写法
@Component
public class CopyItem {

    @Id
    @Field("id")
    private String id;
    @Field("item_title")
    private String item_title;
    @Field("item_price")
    private String item_price;
    @Field("item_image")
    private String item_image;
    @Field("item_goodsId")
    private Long item_goodsId;
    @Field("item_category")
    private String item_category;
    @Field("item_brand")
    private String item_brand;
    @Field("item_seller")
    private String item_seller;

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

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public Long getItem_goodsId() {
        return item_goodsId;
    }

    public void setItem_goodsId(Long item_goodsId) {
        this.item_goodsId = item_goodsId;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getItem_brand() {
        return item_brand;
    }

    public void setItem_brand(String item_brand) {
        this.item_brand = item_brand;
    }

    public String getItem_seller() {
        return item_seller;
    }

    public void setItem_seller(String item_seller) {
        this.item_seller = item_seller;
    }
}
