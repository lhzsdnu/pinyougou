package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.OrderItem;
import com.pinyougou.mapper.OrderItemMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.OrderItemService;
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
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 查询全部
     */
    @Override
    public List<OrderItem> findAll() {
        return orderItemMapper.selectList(new EntityWrapper<OrderItem>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<OrderItem> entity = new EntityWrapper<OrderItem>();
        Page<OrderItem> page = new Page<OrderItem>(pageNum, pageSize);

        List<OrderItem> pageInfoList = orderItemMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(orderItemMapper.selectPage(page, entity));

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
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }


    /**
     * 修改
     */
    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateById(orderItem);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public OrderItem findOne(Long id) {
        return orderItemMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            orderItemMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(OrderItem orderItem, int pageNum, int pageSize) {

        //分页查询
        Wrapper<OrderItem> entity = new EntityWrapper<OrderItem>();

        if (orderItem != null) {

            if (orderItem.getTitle() != null && orderItem.getTitle().length() > 0) {
                entity.like("title", orderItem.getTitle());
                //criteria.andTitleLike("%" + orderItem.getTitle() + "%");
            }
            if (orderItem.getPicPath() != null && orderItem.getPicPath().length() > 0) {
                entity.like("pic_path", orderItem.getPicPath());
                //criteria.andPicPathLike("%" + orderItem.getPicPath() + "%");
            }
            if (orderItem.getSellerId() != null && orderItem.getSellerId().length() > 0) {
                entity.like("seller_id", orderItem.getSellerId());
                //criteria.andSellerIdLike("%" + orderItem.getSellerId() + "%");
            }

        }

        Page<OrderItem> page = new Page<OrderItem>(pageNum, pageSize);

        List<OrderItem> pageInfoList = orderItemMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(orderItemMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
