package com.pinyougou.page.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pinyougou.entity.Goods;
import com.pinyougou.entity.GoodsDesc;
import com.pinyougou.entity.Item;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.mapper.GoodsMapper;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.page.service.ItemPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private ItemMapper itemMapper;


    @JmsListener(destination = "pinyougou_topic_page",containerFactory="jmsListenerContainerTopic")
    public void receiveTopic(Long goodsId) {
        try {
            System.out.println("接收到消息："+goodsId);
            boolean b = this.genItemHtml(goodsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "pinyougou_topic_page_delete",containerFactory="jmsListenerContainerTopic")
    public boolean deleteItemHtml(Long[] goodsIds) {
        try {
            for(Long goodsId:goodsIds){
                String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
                new File(path + "/static/" +goodsId+".html").delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    @Override
    public boolean genItemHtml(Long goodsId) {
        try {
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map dataModel = new HashMap<>();
            //1.加载商品表数据
            Goods goods = goodsMapper.selectById(goodsId);
            dataModel.put("goods", goods);
            //2.加载商品扩展表数据
            GoodsDesc goodsDesc = goodsDescMapper.selectById(goodsId);
            dataModel.put("goodsDesc", goodsDesc);
            //3.商品分类
            String itemCat1 = itemCatMapper.selectById(goods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectById(goods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectById(goods.getCategory3Id()).getName();
            dataModel.put("itemCat1", itemCat1);
            dataModel.put("itemCat2", itemCat2);
            dataModel.put("itemCat3", itemCat3);
            //4.SKU列表
            Wrapper<Item> entity = new EntityWrapper<Item>();
            //状态为有效
            entity.eq("status",1);
            //指定SPU ID
            entity.eq("goods_id",goodsId);
            //按照状态降序，保证第一个为默认
            entity.orderBy("is_default",false);
            List<Item> itemList = itemMapper.selectList(entity);
            dataModel.put("itemList", itemList);

            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            Writer out = new FileWriter(path + "/static/" + goodsId + ".html");
            template.process(dataModel, out);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
