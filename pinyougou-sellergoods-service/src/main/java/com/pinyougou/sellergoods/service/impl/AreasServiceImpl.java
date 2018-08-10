package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Areas;
import com.pinyougou.mapper.AreasMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.AreasService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 行政区域县区信息表 服务实现类
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
public class AreasServiceImpl extends ServiceImpl<AreasMapper, Areas> implements AreasService {

    @Autowired
    private AreasMapper areasMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Areas> findAll() {
        return areasMapper.selectList(new EntityWrapper<Areas>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {

        //分页查询
        Wrapper<Areas> entity = new EntityWrapper<Areas>();
        Page<Areas> page = new Page<Areas>(pageNum, pageSize);

        List<Areas> pageInfoList = areasMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(areasMapper.selectPage(page, entity));

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
    public void add(Areas areas) {
        areasMapper.insert(areas);
    }


    /**
     * 修改
     */
    @Override
    public void update(Areas areas) {
        areasMapper.updateById(areas);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Areas findOne(Long id) {
        return areasMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            areasMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Areas areas, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Areas> entity = new EntityWrapper<Areas>();

        if (areas != null) {

            if (areas.getAreaid() != null && areas.getAreaid().length() > 0) {
                entity.like("areaid", areas.getAreaid());
                //criteria.andAreaidLike("%"+areas.getAreaid()+"%");
            }
            if (areas.getArea() != null && areas.getArea().length() > 0) {
                entity.like("area", areas.getArea());
                //criteria.andAreaLike("%"+areas.getArea()+"%");
            }
            if (areas.getCityid() != null && areas.getCityid().length() > 0) {
                entity.like("cityid", areas.getCityid());
                //criteria.andCityidLike("%"+areas.getCityid()+"%");
            }
        }

        Page<Areas> page = new Page<Areas>(pageNum, pageSize);

        List<Areas> pageInfoList = areasMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(areasMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;

    }

}
