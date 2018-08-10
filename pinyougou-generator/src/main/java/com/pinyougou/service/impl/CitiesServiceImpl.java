package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Cities;
import com.pinyougou.mapper.CitiesMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 行政区域地州市信息表 服务实现类
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
public class CitiesServiceImpl extends ServiceImpl<CitiesMapper, Cities> implements CitiesService {

    @Autowired
    private CitiesMapper citiesMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Cities> findAll() {
        return citiesMapper.selectList(new EntityWrapper<Cities>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Cities> entity = new EntityWrapper<Cities>();
        Page<Cities> page = new Page<Cities>(pageNum, pageSize);

        List<Cities> pageInfoList = citiesMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(citiesMapper.selectPage(page, entity));

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
    public void add(Cities cities) {
        citiesMapper.insert(cities);
    }


    /**
     * 修改
     */
    @Override
    public void update(Cities cities) {
        citiesMapper.updateById(cities);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Cities findOne(Long id) {
        return citiesMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            citiesMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Cities cities, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Cities> entity = new EntityWrapper<Cities>();

        if (cities != null) {

            if (cities.getCityid() != null && cities.getCityid().length() > 0) {
                entity.like("cityid", cities.getCityid());
                //criteria.andCityidLike("%"+cities.getCityid()+"%");
            }
            if (cities.getCity() != null && cities.getCity().length() > 0) {
                entity.like("city", cities.getCity());
                //criteria.andCityLike("%"+cities.getCity()+"%");
            }
            if (cities.getProvinceid() != null && cities.getProvinceid().length() > 0) {
                entity.like("provinceid", cities.getProvinceid());
                //criteria.andProvinceidLike("%"+cities.getProvinceid()+"%");
            }

        }

        Page<Cities> page = new Page<Cities>(pageNum, pageSize);

        List<Cities> pageInfoList = citiesMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(citiesMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
