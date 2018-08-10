package com.pinyougou.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.entity.SeckillOrder;
import com.pinyougou.pojo.PageResult;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
public interface SeckillOrderService extends IService<SeckillOrder> {
    /**
     * 返回全部列表
     *
     * @return
     */
    public List<SeckillOrder> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(SeckillOrder seckillOrder);


    /**
     * 修改
     */
    public void update(SeckillOrder seckillOrder);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public SeckillOrder findOne(Long id);


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
    public PageResult findPage(SeckillOrder seckillOrder, int pageNum, int pageSize);

}
