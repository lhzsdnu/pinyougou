package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Specification;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Specification> findAll() {
        return specificationMapper.selectList(new EntityWrapper<Specification>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Specification> entity = new EntityWrapper<Specification>();
        Page<Specification> page = new Page<Specification>(pageNum, pageSize);

        List<Specification> pageInfoList = specificationMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(specificationMapper.selectPage(page, entity));

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
    public void add(Specification specification) {
        specificationMapper.insert(specification);
    }


    /**
     * 修改
     */
    @Override
    public void update(Specification specification) {
        specificationMapper.updateById(specification);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Specification findOne(Long id) {
        return specificationMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Specification specification, int pageNum, int pageSize) {
        //分页查询
        Wrapper<Specification> entity = new EntityWrapper<Specification>();

        if (specification != null) {
            if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
                entity.like("spec_name", specification.getSpecName());
                //criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
            }

        }

        Page<Specification> page = new Page<Specification>(pageNum, pageSize);

        List<Specification> pageInfoList = specificationMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(specificationMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }
}
