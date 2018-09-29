package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.config.ChangeToPinYinJP;
import com.pinyougou.config.MusicRepository;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.CopyItem;
import com.pinyougou.redis.RedisUtil;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}",
        timeout = 3000
)
@Transactional
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ChangeToPinYinJP changeToPinYinJP;

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        //putAll可以合并两个Map，只不过如果有相同的key那么用后面的覆盖前面的

        //1.查询列表
        map.putAll(searchList(searchMap));

        //2.根据关键字查询商品分类
        List categoryList = searchCategoryList(searchMap);
        map.put("categoryList", categoryList);

        //3.查询品牌和规格列表
        if (categoryList.size() > 0) {
            map.putAll(searchBrandAndSpecList(categoryList.get(0).toString()));
        }
        return map;
    }

    private Map searchList(Map searchMap) {
        Map<String, Object> map = new HashMap<>();

        //按照关键字查询
        String keywords = searchMap.get("keywords").toString();
        String category = searchMap.get("category").toString();
        String brand = searchMap.get("brand").toString();

        Map<String, String> specMap = (Map) searchMap.get("spec");
        Map<String, String> mapSpec = new HashMap<String, String>();
        for (String key : specMap.keySet()) {
            String keySpec = changeToPinYinJP.changeToTonePinYinNoSpace(key);
            String valueSpec = specMap.get(key);
        }

        Pageable pageable = PageRequest.of(0, 20);

        // 添加查询条件
        // HighlightPage为返回的高亮页对象
        HighlightPage<CopyItem> page = musicRepository.findByKeywords(keywords, category, brand, pageable);

        // HighlightEntry 高亮入口
        // List<HighlightEntry<T>> getHighlighted() 高亮入口集合(循环),实际上是对应的每条记录
        for (HighlightEntry<CopyItem> h : page.getHighlighted()) {

            //获取原实体类
            CopyItem item = h.getEntity();
            //获取高亮列表(高亮域/列的个数，即fields属性)  @Highlight(fields ={"item_title"})
            //List<Highlight> highlightsList = entry.getHighlights();
            //获取每个域有可能存储多值   <field name="item_keywords" multiValued="true"/>
            //List<String> snipplets = h.getSnipplets();
            //获取要高亮的内容
            //snipplets.get(0)
            if (h.getHighlights().size() > 0 && h.getHighlights().get(0).getSnipplets().size() > 0) {
                //设置高亮的结果
                item.setTitle(h.getHighlights().get(0).getSnipplets().get(0));
            }
        }

        map.put("rows", page.getContent());
        return map;
    }

    /**
     * 查询分类列表
     *
     * @param searchMap
     * @return
     */
    private List searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList();

        //按照关键字查询
        String keywords = searchMap.get("keywords").toString();
        Pageable pageable = PageRequest.of(0, 20);

        //设置分组选项,得到分组页
        FacetPage<CopyItem> page = musicRepository.findByKeywords(keywords, pageable);
        //得到分组结果入口页
        Page<FacetFieldEntry> groupEntries = page.getFacetResultPage("item_category");
        //得到分组入口集合
        Iterator<FacetFieldEntry> content = groupEntries.iterator();
        while (content.hasNext()) {
            //将分组结果的名称封装到返回值中
            list.add(content.next().getValue());
        }

        return list;
    }

    /**
     * 查询品牌和规格列表
     *
     * @param category 分类名称
     * @return
     */
    private Map searchBrandAndSpecList(String category) {
        Map map = new HashMap();
        //根据分类名称获取模板ID
        Integer typeId = (Integer) redisUtil.hget("itemCat", category);
        if (typeId != null) {
            //根据模板ID查询品牌列表
            List brandList = (List) redisUtil.hget("brandList", typeId.toString());
            System.out.println("从缓存中获取品牌列表");
            map.put("brandList", brandList);
            //根据模板ID查询规格列表
            List specList = (List) redisUtil.hget("specList", typeId.toString());
            System.out.println("从缓存中获取规格列表");
            map.put("specList", specList);
        }
        return map;
    }


}
