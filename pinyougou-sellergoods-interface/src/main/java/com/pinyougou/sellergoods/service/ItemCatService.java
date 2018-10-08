package com.pinyougou.sellergoods.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.entity.ItemCat;
import com.pinyougou.pojo.PageResult;

import java.util.List;

/**
 * <p>
 * 商品类目 服务类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
public interface ItemCatService extends IService<ItemCat> {
    /**
     * 返回全部列表
     *
     * @return
     */
    public List<ItemCat> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(ItemCat itemCat);


    /**
     * 修改
     */
    public void update(ItemCat itemCat);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public ItemCat findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public String  delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(ItemCat itemCat, int pageNum, int pageSize);

    /**
     * 根据上级ID返回列表
     * @return
     */
    public List<ItemCat> findByParentId(Long parentId);


}
