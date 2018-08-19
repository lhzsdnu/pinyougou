package com.pinyougou.sellergoods.grouppojo;

import com.pinyougou.entity.Goods;
import com.pinyougou.entity.GoodsDesc;
import com.pinyougou.entity.Item;

import java.io.Serializable;
import java.util.List;

public class TbGoods implements Serializable {

    private Goods goods;//商品SPU
    private GoodsDesc goodsDesc;//商品扩展
    private List<Item> itemList;//商品SKU列表

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
