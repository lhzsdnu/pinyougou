package com.pinyougou.sellergoods.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.entity.TypeTemplate;
import com.pinyougou.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
public interface TypeTemplateService extends IService<TypeTemplate> {
    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TypeTemplate> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(TypeTemplate typeTemplate);


    /**
     * 修改
     */
    public void update(TypeTemplate typeTemplate);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TypeTemplate findOne(Long id);


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
    public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize);

    /**
     * 模板下拉框数据
     */
    List<Map> selectOptionList();

    /**
     * 返回规格列表
     */
    public List<Map> findSpecList(Long id);


}
