package com.pinyougou.sellergoods.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.entity.Goods;
import com.pinyougou.entity.Item;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.grouppojo.TbGoods;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 返回全部列表
     *
     * @return
     */
    public List<Goods> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(TbGoods goods);


    /**
     * 修改
     */
    public void update(TbGoods goods);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TbGoods findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(Goods goods, int pageNum, int pageSize);

    /**
     * 批量修改状态
     * @param ids
     * @param status
     */
    public void updateStatus(Long []ids,String status);


    /**
     * 根据商品ID和状态查询Item表信息
     * @param goodsIds
     * @param status
     */
    public List<Item> findItemListByGoodsIdandStatus(Long[] goodsIds, String status );


}
