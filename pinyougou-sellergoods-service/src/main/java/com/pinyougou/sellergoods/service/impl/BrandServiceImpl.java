package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Brand;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.BrandService;
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
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectList(new EntityWrapper<Brand>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Brand> entity = new EntityWrapper<Brand>();
        Page<Brand> page = new Page<Brand>(pageNum, pageSize);

        List<Brand> pageInfoList = brandMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(brandMapper.selectPage(page, entity));

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
    public void add(Brand brand) {
        brandMapper.insert(brand);
    }


    /**
     * 修改
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateById(brand);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Brand findOne(Long id) {
        return brandMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Brand brand, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Brand> entity = new EntityWrapper<Brand>();

        if (brand != null) {
            if (brand.getName() != null && brand.getName().length() > 0) {
                entity.like("name", brand.getName());
                //criteria.andNameLike("%" + brand.getName() + "%");
            }
            if (brand.getFirstChar() != null && brand.getFirstChar().length() > 0) {
                entity.like("first_char", brand.getFirstChar());
                //criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");
            }

        }

        Page<Brand> page = new Page<Brand>(pageNum, pageSize);

        List<Brand> pageInfoList = brandMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(brandMapper.selectPage(page, entity));

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
        return brandMapper.selectOptionList();
    }



}
