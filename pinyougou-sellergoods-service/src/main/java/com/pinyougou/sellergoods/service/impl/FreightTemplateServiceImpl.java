package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.FreightTemplate;
import com.pinyougou.mapper.FreightTemplateMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.FreightTemplateService;
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
public class FreightTemplateServiceImpl extends ServiceImpl<FreightTemplateMapper, FreightTemplate> implements FreightTemplateService {

    @Autowired
    private FreightTemplateMapper freightTemplateMapper;

    /**
     * 查询全部
     */
    @Override
    public List<FreightTemplate> findAll() {
        return freightTemplateMapper.selectList(new EntityWrapper<FreightTemplate>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<FreightTemplate> entity = new EntityWrapper<FreightTemplate>();
        Page<FreightTemplate> page = new Page<FreightTemplate>(pageNum, pageSize);

        List<FreightTemplate> pageInfoList = freightTemplateMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(freightTemplateMapper.selectPage(page, entity));

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
    public void add(FreightTemplate freightTemplate) {
        freightTemplateMapper.insert(freightTemplate);
    }


    /**
     * 修改
     */
    @Override
    public void update(FreightTemplate freightTemplate) {
        freightTemplateMapper.updateById(freightTemplate);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public FreightTemplate findOne(Long id) {
        return freightTemplateMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            freightTemplateMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(FreightTemplate freightTemplate, int pageNum, int pageSize) {

        //分页查询
        Wrapper<FreightTemplate> entity = new EntityWrapper<FreightTemplate>();

        if (freightTemplate != null) {
            if (freightTemplate.getSellerId() != null && freightTemplate.getSellerId().length() > 0) {
                entity.like("seller_id", freightTemplate.getSellerId());
                //criteria.andSellerIdLike("%"+freightTemplate.getSellerId()+"%");
            }
            if (freightTemplate.getIsDefault() != null && freightTemplate.getIsDefault().length() > 0) {
                entity.like("is_default", freightTemplate.getIsDefault());
                //criteria.andIsDefaultLike("%"+freightTemplate.getIsDefault()+"%");
            }
            if (freightTemplate.getName() != null && freightTemplate.getName().length() > 0) {
                entity.like("name", freightTemplate.getName());
                //criteria.andNameLike("%"+freightTemplate.getName()+"%");
            }
            if (freightTemplate.getSendTimeType() != null && freightTemplate.getSendTimeType().length() > 0) {
                entity.like("send_time_type", freightTemplate.getSendTimeType());
                //criteria.andSendTimeTypeLike("%"+freightTemplate.getSendTimeType()+"%");
            }

        }

        Page<FreightTemplate> page = new Page<FreightTemplate>(pageNum, pageSize);

        List<FreightTemplate> pageInfoList = freightTemplateMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(freightTemplateMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;


    }

}
