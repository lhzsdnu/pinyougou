package com.pinyougou.sellergoods.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.entity.Provinces;
import com.pinyougou.pojo.PageResult;

import java.util.List;

/**
 * <p>
 * 省份信息表 服务类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
public interface ProvincesService extends IService<Provinces> {
    /**
     * 返回全部列表
     *
     * @return
     */
    public List<Provinces> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(Provinces provinces);


    /**
     * 修改
     */
    public void update(Provinces provinces);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public Provinces findOne(Long id);


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
    public PageResult findPage(Provinces provinces, int pageNum, int pageSize);
}
