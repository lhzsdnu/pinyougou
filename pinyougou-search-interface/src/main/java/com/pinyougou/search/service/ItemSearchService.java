package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
    /**
     * 关键字搜索
     */
    public Map<String, Object> search(Map searchMap);

    /**
     * 导入数据
     */
    public void importList(List list);

    /**
     * 删除数据
     * @param goodsIdList
     */
    public void deleteByGoodsIds(List goodsIdList);


}
