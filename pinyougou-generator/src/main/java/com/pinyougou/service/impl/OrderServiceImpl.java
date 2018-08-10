package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Order;
import com.pinyougou.mapper.OrderMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.OrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Order> findAll() {
        return orderMapper.selectList(new EntityWrapper<Order>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {

        //分页查询
        Wrapper<Order> entity = new EntityWrapper<Order>();
        Page<Order> page = new Page<Order>(pageNum, pageSize);

        List<Order> pageInfoList = orderMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(orderMapper.selectPage(page, entity));

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
    public void add(Order order) {
        orderMapper.insert(order);
    }


    /**
     * 修改
     */
    @Override
    public void update(Order order) {
        orderMapper.updateById(order);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Order findOne(Long id) {
        return orderMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            orderMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Order order, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Order> entity = new EntityWrapper<Order>();

        if (order != null) {

            if (order.getPaymentType() != null && order.getPaymentType().length() > 0) {
                entity.like("payment_type", order.getPaymentType());
                //criteria.andPaymentTypeLike("%"+order.getPaymentType()+"%");
            }
            if (order.getPostFee() != null && order.getPostFee().length() > 0) {
                entity.like("post_fee", order.getPostFee());
                //criteria.andPostFeeLike("%"+order.getPostFee()+"%");
            }
            if (order.getStatus() != null && order.getStatus().length() > 0) {
                entity.like("status", order.getStatus());
                //criteria.andStatusLike("%"+order.getStatus()+"%");
            }
            if (order.getShippingName() != null && order.getShippingName().length() > 0) {
                entity.like("shipping_name", order.getShippingName());
                //criteria.andShippingNameLike("%"+order.getShippingName()+"%");
            }
            if (order.getShippingCode() != null && order.getShippingCode().length() > 0) {
                entity.like("shipping_code", order.getShippingCode());
                //criteria.andShippingCodeLike("%"+order.getShippingCode()+"%");
            }
            if (order.getUserId() != null && order.getUserId().length() > 0) {
                entity.like("user_id", order.getUserId());
                //criteria.andUserIdLike("%"+order.getUserId()+"%");
            }
            if (order.getBuyerMessage() != null && order.getBuyerMessage().length() > 0) {
                entity.like("buyer_message", order.getBuyerMessage());
                //criteria.andBuyerMessageLike("%"+order.getBuyerMessage()+"%");
            }
            if (order.getBuyerNick() != null && order.getBuyerNick().length() > 0) {
                entity.like("buyer_nick", order.getBuyerNick());
                //criteria.andBuyerNickLike("%"+order.getBuyerNick()+"%");
            }
            if (order.getBuyerRate() != null && order.getBuyerRate().length() > 0) {
                entity.like("buyer_rate", order.getBuyerRate());
                //criteria.andBuyerRateLike("%"+order.getBuyerRate()+"%");
            }
            if (order.getReceiverAreaName() != null && order.getReceiverAreaName().length() > 0) {
                entity.like("receiver_area_name", order.getReceiverAreaName());
                //criteria.andReceiverAreaNameLike("%"+order.getReceiverAreaName()+"%");
            }
            if (order.getReceiverMobile() != null && order.getReceiverMobile().length() > 0) {
                entity.like("receiver_mobile", order.getReceiverMobile());
                //criteria.andReceiverMobileLike("%"+order.getReceiverMobile()+"%");
            }
            if (order.getReceiverZipCode() != null && order.getReceiverZipCode().length() > 0) {
                entity.like("receiver_zip_code", order.getReceiverZipCode());
                //criteria.andReceiverZipCodeLike("%"+order.getReceiverZipCode()+"%");
            }
            if (order.getReceiver() != null && order.getReceiver().length() > 0) {
                entity.like("receiver", order.getReceiver());
                //criteria.andReceiverLike("%"+order.getReceiver()+"%");
            }
            if (order.getInvoiceType() != null && order.getInvoiceType().length() > 0) {
                entity.like("invoice_type", order.getInvoiceType());
                //criteria.andInvoiceTypeLike("%"+order.getInvoiceType()+"%");
            }
            if (order.getSourceType() != null && order.getSourceType().length() > 0) {
                entity.like("source_type", order.getSourceType());
                //criteria.andSourceTypeLike("%"+order.getSourceType()+"%");
            }
            if (order.getSellerId() != null && order.getSellerId().length() > 0) {
                entity.like("seller_id", order.getSellerId());
                //criteria.andSellerIdLike("%"+order.getSellerId()+"%");
            }

        }

        Page<Order> page = new Page<Order>(pageNum, pageSize);

        List<Order> pageInfoList = orderMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(orderMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }
}
