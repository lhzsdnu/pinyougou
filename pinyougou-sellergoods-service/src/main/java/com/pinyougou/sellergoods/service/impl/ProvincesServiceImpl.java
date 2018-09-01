package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Provinces;
import com.pinyougou.mapper.ProvincesMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 省份信息表 服务实现类
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
public class ProvincesServiceImpl extends ServiceImpl<ProvincesMapper, Provinces> implements ProvincesService {

    @Autowired
    private ProvincesMapper provincesMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Provinces> findAll() {
        return provincesMapper.selectList(new EntityWrapper<Provinces>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Provinces> entity = new EntityWrapper<Provinces>();
        Page<Provinces> page = new Page<Provinces>(pageNum, pageSize);

        List<Provinces> pageInfoList = provincesMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(provincesMapper.selectPage(page, entity));

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
    public void add(Provinces provinces) {
        provincesMapper.insert(provinces);
    }


    /**
     * 修改
     */
    @Override
    public void update(Provinces provinces) {
        provincesMapper.updateById(provinces);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Provinces findOne(Long id) {
        return provincesMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            provincesMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Provinces provinces, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Provinces> entity = new EntityWrapper<Provinces>();

        if (provinces != null) {
            if (provinces.getProvinceid() != null && provinces.getProvinceid().length() > 0) {
                entity.like("provinceid", provinces.getProvinceid());
                //criteria.andProvinceidLike("%"+provinces.getProvinceid()+"%");
            }
            if (provinces.getProvince() != null && provinces.getProvince().length() > 0) {
                entity.like("province", provinces.getProvince());
                //criteria.andProvinceLike("%"+provinces.getProvince()+"%");
            }

        }

        Page<Provinces> page = new Page<Provinces>(pageNum, pageSize);

        List<Provinces> pageInfoList = provincesMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(provincesMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
