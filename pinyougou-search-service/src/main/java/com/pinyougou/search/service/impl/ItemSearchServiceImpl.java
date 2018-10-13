package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pinyougou.config.ChangeToPinYinJP;
import com.pinyougou.config.MusicRepository;
import com.pinyougou.entity.Item;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.CopyItem;
import com.pinyougou.redis.RedisUtil;
import com.pinyougou.search.service.ItemSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
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

        //关键字空格处理
        String keywords = (String) searchMap.get("keywords");
        searchMap.put("keywords", keywords.replace(" ", ""));

        //1.查询列表
        map.putAll(searchList(searchMap));

        //2.根据关键字查询商品分类
        List categoryList = searchCategoryList(searchMap);
        map.put("categoryList", categoryList);

        //3.查询品牌和规格列表
        String categoryName = (String) searchMap.get("category");
        if (!"".equals(categoryName)) {//如果有分类名称
            map.putAll(searchBrandAndSpecList(categoryName));
        } else {//如果没有分类名称，按照第一个查询
            if (categoryList.size() > 0) {
                map.putAll(searchBrandAndSpecList(categoryList.get(0).toString()));
            }
        }

        return map;
    }

    private Map searchList(Map searchMap) {

        // 1.1 构建高亮域选项
        HighlightQuery query = new SimpleHighlightQuery();
        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);
        // 1.2 设置查询条件
        String keywords = (String) searchMap.get("keywords");
        Criteria criteria = new Criteria("item_keywords").is(keywords);

        query.addCriteria(criteria);
        // 1.3 设置分类过滤
        if (StringUtils.isNotBlank((String) searchMap.get("category"))) {
            Criteria categoryCriteria = new Criteria("item_category").is(searchMap.get("category"));
            FilterQuery filterQuery = new SimpleFilterQuery().addCriteria(categoryCriteria);
            query.addFilterQuery(filterQuery);
        }
        // 1.4 设置品牌过滤
        if (StringUtils.isNotBlank((String) searchMap.get("brand"))) {
            Criteria brandCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            FilterQuery filterQuery = new SimpleFilterQuery().addCriteria(brandCriteria);
            query.addFilterQuery(filterQuery);
        }
        // 1.5 设置规格选项过滤(数据格式Map动态域)
        if (searchMap.get("spec") != null) {
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                Criteria specCriteria = new Criteria("item_spec_" + changeToPinYinJP.changeToTonePinYinNoSpace(key)).is(specMap.get(key));
                FilterQuery filterQuery = new SimpleFilterQuery().addCriteria(specCriteria);
                query.addFilterQuery(filterQuery);
            }
        }
        // 1.6 价格区间过滤
        if (StringUtils.isNotBlank((String) searchMap.get("price"))) {
            String[] price = ((String) searchMap.get("price")).split("-");
            // 最小价格不等于0
            if (!"0".equals(price[0])) {
                Criteria priceCriteria = new Criteria("item_price").greaterThanEqual(price[0]);
                FilterQuery filterQuery = new SimpleFilterQuery().addCriteria(priceCriteria);
                query.addFilterQuery(filterQuery);
            }
            // 不等于最大价格,不做限制
            if (!"*".equals(price[1])) {
                Criteria priceCriteria = new Criteria("item_price").lessThanEqual(price[1]);
                FilterQuery filterQuery = new SimpleFilterQuery().addCriteria(priceCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        Integer pageNo = (Integer) searchMap.get("pageNo");//提取页码
        if (pageNo == null) {
            pageNo = 1;//默认第一页
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");//每页记录数
        if (pageSize == null) {
            pageSize = 20;//默认20
        }
        query.setOffset(Long.parseLong(String.valueOf(((pageNo - 1) * pageSize))));//从第几条记录查询
        query.setRows(pageSize);


        //排序
        String sortValue = (String) searchMap.get("sort");//ASC  DESC
        String sortField = (String) searchMap.get("sortField");//排序字段
        if (sortValue != null && !sortValue.equals("")) {
            if (sortValue.equals("ASC")) {
                Sort sort = new Sort(Sort.Direction.ASC, "item_" + sortField);
                query.addSort(sort);
            }
            if (sortValue.equals("DESC")) {
                Sort sort = new Sort(Sort.Direction.DESC, "item_" + sortField);
                query.addSort(sort);
            }
        }

        // 1.7 执行查询并处理结果
        HighlightPage<CopyItem> pageResult = solrTemplate.queryForHighlightPage("new_core", query, CopyItem.class);
        // 获取高亮入口集合
        List<HighlightEntry<CopyItem>> entrys = pageResult.getHighlighted();
        for (HighlightEntry<CopyItem> highlightEntry : entrys) {
            // 获取所有高亮域
            List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
            // 可能存在多值,得到第一个域的第一个值得高亮
            if (highlights != null && highlights.size() > 0 && highlights.get(0).getSnipplets() != null
                    && highlights.get(0).getSnipplets().size() > 0) {
                CopyItem entity = highlightEntry.getEntity();
                String highlightTitle = highlights.get(0).getSnipplets().get(0);
                entity.setTitle(highlightTitle);
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("rows", pageResult.getContent());
        resultMap.put("totalPages", pageResult.getTotalPages());//返回总页数
        resultMap.put("total", pageResult.getTotalElements());//返回总记录数

        return resultMap;

    }

    /**
     * 查询分类列表
     *
     * @param searchMap
     * @return
     */
    private List searchCategoryList(Map searchMap) {
        List<String> searchList = new ArrayList<String>();
        // 构建分页 group by
        FacetQuery query = new SimpleFacetQuery();
        FacetOptions facetOptions = new FacetOptions().addFacetOnField("item_category");
        query.setFacetOptions(facetOptions);
        // 构建关键字查询 where
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        // 执行查询,获取分组页对象
        FacetPage<CopyItem> groupPage = solrTemplate.queryForFacetPage("new_core", query, CopyItem.class);

        //得到分组结果入口页
        Page<FacetFieldEntry> groupEntries = groupPage.getFacetResultPage("item_category");
        //得到分组入口集合
        Iterator<FacetFieldEntry> content = groupEntries.iterator();
        while (content.hasNext()) {
            //将分组结果的名称封装到返回值中
            searchList.add(content.next().getValue());
        }

        return searchList;
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

    @Override
    public void importList(List<Item> list) {
        List<CopyItem> copyItemList = new ArrayList<CopyItem>();
        for (Item item : list) {
            CopyItem copyItem = new CopyItem();
            copyItem.setId(String.valueOf(item.getId()));
            copyItem.setBrand(item.getBrand());
            copyItem.setCategory(item.getCategory());
            copyItem.setGoodsId(item.getGoodsId());
            copyItem.setImage(item.getImage());
            copyItem.setPrice(item.getPrice().doubleValue());
            copyItem.setSeller(item.getSeller());
            copyItem.setTitle(item.getTitle());

            //将spec字段中的json字符串转换为map
            Map<String, String> specMap = JSON.parseObject(item.getSpec(), Map.class);
            //changeToPinYinJP.changeToTonePinYinNoSpace();
            Map<String, String> map = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                String key = changeToPinYinJP.changeToTonePinYinNoSpace(entry.getKey());
                String value = entry.getValue();
                map.put(key, value);
            }
            //给带注解的字段赋值
            copyItem.setSpecMap(map);

            copyItemList.add(copyItem);

            musicRepository.saveAll(copyItemList);
        }
    }

    @Override
    public void deleteByGoodsIds(List<Long> goodsIdList) {
        //System.out.println("删除商品ID" + goodsIdList);
        //Query query = new SimpleQuery();
        //Criteria criteria = new Criteria("item_goodsId").in(goodsIdList);
        //query.addCriteria(criteria);
        //solrTemplate.delete("new_core", query);
        //solrTemplate.commit("new_core");
        musicRepository.deleteByGoodsIdIn(goodsIdList);

    }


}
