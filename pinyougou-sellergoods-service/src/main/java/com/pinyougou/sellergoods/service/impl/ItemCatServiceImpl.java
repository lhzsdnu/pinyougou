package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.ItemCat;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
@Transactional
public class ItemCatServiceImpl extends ServiceImpl<ItemCatMapper, ItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 查询全部
     */
    @Override
    public List<ItemCat> findAll() {
        return itemCatMapper.selectList(new EntityWrapper<ItemCat>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<ItemCat> entity = new EntityWrapper<ItemCat>();
        Page<ItemCat> page = new Page<ItemCat>(pageNum, pageSize);

        List<ItemCat> pageInfoList = itemCatMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(itemCatMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

    /**
     * 增加
     */
    @Override
    public void add(ItemCat itemCat) {
        itemCatMapper.insert(itemCat);
    }


    /**
     * 修改
     */
    @Override
    public void update(ItemCat itemCat) {
        itemCatMapper.updateById(itemCat);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public ItemCat findOne(Long id) {
        return itemCatMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public String delete(Long[] ids) {

        String str="";

        for (Long id : ids) {

            //只能删除没有下级分类，后台校验
            Wrapper<ItemCat> entity = new EntityWrapper<ItemCat>();
            entity.eq("parent_Id",id);
            List<ItemCat> list=itemCatMapper.selectList(entity);
            if (list.size()>0){
                System.out.println("存在下级分类，不可删除");
                str=str+"分类ID："+id+" 存在下级分类，不可删除"+System.lineSeparator();
            }else{
                itemCatMapper.deleteById(id);
            }

        }

        return str;
    }


    @Override
    public PageResult findPage(ItemCat itemCat, int pageNum, int pageSize) {

        //分页查询
        Wrapper<ItemCat> entity = new EntityWrapper<ItemCat>();

        if (itemCat != null) {
            if (itemCat.getName() != null && itemCat.getName().length() > 0) {
                entity.like("name", itemCat.getName());
                //criteria.andNameLike("%" + itemCat.getName() + "%");
            }

        }

        Page<ItemCat> page = new Page<ItemCat>(pageNum, pageSize);

        List<ItemCat> pageInfoList = itemCatMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(itemCatMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

    /**
     * 根据上级ID查询列表
     */
    @Override
    public List<ItemCat> findByParentId(Long parentId) {

        Wrapper<ItemCat> entity = new EntityWrapper<ItemCat>();
        entity.eq("parent_Id",parentId);
        return itemCatMapper.selectList(entity);

    }


}
