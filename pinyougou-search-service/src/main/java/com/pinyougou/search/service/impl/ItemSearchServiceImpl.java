package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.config.MusicRepository;
import com.pinyougou.config.MusicRepositoryGroup;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.CopyItem;
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
    private MusicRepositoryGroup musicRepositoryGroup;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        //putAll可以合并两个Map，只不过如果有相同的key那么用后面的覆盖前面的

        //1.查询列表
        map.putAll(searchList(searchMap));

        //2.根据关键字查询商品分类
        List categoryList = searchCategoryList(searchMap);
        map.put("categoryList", categoryList);

        return map;
    }

    private Map searchList(Map searchMap) {
        Map<String, Object> map = new HashMap<>();

        //按照关键字查询
        String str = searchMap.get("keywords").toString();
        Pageable pageable = PageRequest.of(0, 20);

        // 添加查询条件
        // HighlightPage为返回的高亮页对象
        HighlightPage<CopyItem> page = musicRepository.findByKeywordsContaining(str, pageable);

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
                System.out.println("@@@@"+h.getHighlights().get(0).getSnipplets().get(0));
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
        String str = searchMap.get("keywords").toString();
        Pageable pageable = PageRequest.of(0, 20);

        //设置分组选项,得到分组页
        FacetPage<CopyItem> page = musicRepositoryGroup.findByKeywordsContaining(str, pageable);
        //得到分组结果入口页
        Page<FacetFieldEntry>  groupEntries = page.getFacetResultPage("item_category");
        //得到分组入口集合
        Iterator<FacetFieldEntry> content = groupEntries.iterator();
        while(content.hasNext()){
            //将分组结果的名称封装到返回值中
            list.add(content.next().getValue());
        }
        return list;
    }


}
