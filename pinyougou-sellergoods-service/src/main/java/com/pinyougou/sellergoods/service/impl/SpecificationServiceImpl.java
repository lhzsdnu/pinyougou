package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Specification;
import com.pinyougou.entity.SpecificationOption;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.grouppojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
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
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;


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
    public void add(TbSpecification tbSpecification) {
        //插入规格
        specificationMapper.insert(tbSpecification.getSpecification());
        //循环插入规格选项
        for (SpecificationOption specificationOption :
                tbSpecification.getSpecificationOptionList()) {
            //设置规格ID
            specificationOption.setSpecId(tbSpecification.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(TbSpecification tbSpecification) {
        //保存修改的规格
        specificationMapper.updateById(tbSpecification.getSpecification());//保存规格
        //删除原有的规格选项
        Wrapper<SpecificationOption> entity = new EntityWrapper<SpecificationOption>();
        entity.eq("spec_id", tbSpecification.getSpecification().getId());
        specificationOptionMapper.delete(entity);

        //循环插入规格选项
        for (SpecificationOption specificationOption :
                tbSpecification.getSpecificationOptionList()) {
            specificationOption.setSpecId(tbSpecification.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }

    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbSpecification findOne(Long id) {
        //查询规格
        Specification specification = specificationMapper.selectById(id);

        Wrapper<SpecificationOption> entity = new EntityWrapper<SpecificationOption>();
        entity.eq("spec_id", id);
        //查询规格选项列表
        List<SpecificationOption> optionList = specificationOptionMapper.selectList(entity);

        //构建组合实体类返回结果
        TbSpecification spec = new TbSpecification();
        spec.setSpecification(specification);
        spec.setSpecificationOptionList(optionList);

        return spec;
    }


    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteById(id);
            //删除原有的规格选项
            Wrapper<SpecificationOption> entity =
                    new EntityWrapper<SpecificationOption>();
            entity.eq("spec_id", id);
            specificationOptionMapper.delete(entity);

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

    /**
     * 列表数据
     */
    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
