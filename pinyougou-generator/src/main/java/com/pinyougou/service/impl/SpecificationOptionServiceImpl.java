package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.SpecificationOption;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.SpecificationOptionService;
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
public class SpecificationOptionServiceImpl extends ServiceImpl<SpecificationOptionMapper, SpecificationOption> implements SpecificationOptionService {

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<SpecificationOption> findAll() {
        return specificationOptionMapper.selectList(new EntityWrapper<SpecificationOption>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<SpecificationOption> entity = new EntityWrapper<SpecificationOption>();
        Page<SpecificationOption> page = new Page<SpecificationOption>(pageNum, pageSize);

        List<SpecificationOption> pageInfoList = specificationOptionMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(specificationOptionMapper.selectPage(page, entity));

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
    public void add(SpecificationOption specificationOption) {
        specificationOptionMapper.insert(specificationOption);
    }


    /**
     * 修改
     */
    @Override
    public void update(SpecificationOption specificationOption) {
        specificationOptionMapper.updateById(specificationOption);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public SpecificationOption findOne(Long id) {
        return specificationOptionMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationOptionMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(SpecificationOption specificationOption, int pageNum, int pageSize) {

        //分页查询
        Wrapper<SpecificationOption> entity = new EntityWrapper<SpecificationOption>();

        if (specificationOption != null) {
            if (specificationOption.getOptionName() != null && specificationOption.getOptionName().length() > 0) {
                entity.like("option_name", specificationOption.getOptionName());
                //criteria.andOptionNameLike("%" + specificationOption.getOptionName() + "%");
            }

        }

        Page<SpecificationOption> page = new Page<SpecificationOption>(pageNum, pageSize);

        List<SpecificationOption> pageInfoList = specificationOptionMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(specificationOptionMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
