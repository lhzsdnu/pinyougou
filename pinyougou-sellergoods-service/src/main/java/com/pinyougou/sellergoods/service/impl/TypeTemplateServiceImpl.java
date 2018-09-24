package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.SpecificationOption;
import com.pinyougou.entity.TypeTemplate;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.redis.RedisUtil;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
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
public class TypeTemplateServiceImpl extends ServiceImpl<TypeTemplateMapper, TypeTemplate> implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 查询全部
     */
    @Override
    public List<TypeTemplate> findAll() {
        return typeTemplateMapper.selectList(new EntityWrapper<TypeTemplate>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<TypeTemplate> entity = new EntityWrapper<TypeTemplate>();
        Page<TypeTemplate> page = new Page<TypeTemplate>(pageNum, pageSize);

        List<TypeTemplate> pageInfoList = typeTemplateMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(typeTemplateMapper.selectPage(page, entity));

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
    public void add(TypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }


    /**
     * 修改
     */
    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateById(typeTemplate);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {

        //分页查询
        Wrapper<TypeTemplate> entity = new EntityWrapper<TypeTemplate>();

        if (typeTemplate != null) {
            if (typeTemplate.getName() != null && typeTemplate.getName().length() > 0) {
                entity.like("name", typeTemplate.getName());
                //criteria.andNameLike("%" + typeTemplate.getName() + "%");
            }
            if (typeTemplate.getSpecIds() != null && typeTemplate.getSpecIds().length() > 0) {
                entity.like("spec_ids", typeTemplate.getSpecIds());
                //criteria.andSpecIdsLike("%" + typeTemplate.getSpecIds() + "%");
            }
            if (typeTemplate.getBrandIds() != null && typeTemplate.getBrandIds().length() > 0) {
                entity.like("brand_ids", typeTemplate.getBrandIds());
                //criteria.andBrandIdsLike("%" + typeTemplate.getBrandIds() + "%");
            }
            if (typeTemplate.getCustomAttributeItems() != null && typeTemplate.getCustomAttributeItems().length() > 0) {
                entity.like("custom_attribute_items", typeTemplate.getCustomAttributeItems());
                //criteria.andCustomAttributeItemsLike("%" + typeTemplate.getCustomAttributeItems() + "%");
            }

        }

        Page<TypeTemplate> page = new Page<TypeTemplate>(pageNum, pageSize);

        List<TypeTemplate> pageInfoList = typeTemplateMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(typeTemplateMapper.selectPage(page, entity));


        saveToRedis();//存入数据到缓存

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

    /**
     * 列表数据
     */
    @Override
    public List<Map> selectOptionList() {
        return typeTemplateMapper.selectOptionList();
    }

    @Override
    public List<Map> findSpecList(Long aLong) {

        //根据模板id查询模板实体
        TypeTemplate typeTemplate = typeTemplateMapper.selectById(aLong);
        //模板对应的规格列表
        //[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = typeTemplate.getSpecIds();

        List<Map> list = JSON.parseArray(specIds, Map.class);

        for (Map map : list) {
            //查询规格选项列表

            //条件查询
            Wrapper<SpecificationOption> entity = new EntityWrapper<SpecificationOption>();
            entity.eq("spec_id", (Integer) map.get("id"));
            List<SpecificationOption> options = specificationOptionMapper.selectList(entity);
            map.put("options", options);
        }

        return list;

    }

    /**
     * 将数据存入缓存
     */
    private void saveToRedis(){
        //获取模板数据
        List<TypeTemplate> typeTemplateList = findAll();
        //循环模板
        for(TypeTemplate typeTemplate :typeTemplateList){
            //存储品牌列表
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            redisUtil.hset("brandList",typeTemplate.getId().toString(), brandList);
            System.out.println("品牌列表添加到缓存中");
            //存储规格列表
            //根据模板ID查询规格列表
            List<Map> specList = findSpecList(typeTemplate.getId());
            redisUtil.hset("specList",typeTemplate.getId().toString(), specList);
            System.out.println("规格列表添加到缓存中");
        }
    }


}
