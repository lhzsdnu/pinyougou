package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.SeckillOrder;
import com.pinyougou.mapper.SeckillOrderMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.SeckillOrderService;
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
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    /**
     * 查询全部
     */
    @Override
    public List<SeckillOrder> findAll() {
        return seckillOrderMapper.selectList(new EntityWrapper<SeckillOrder>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<SeckillOrder> entity = new EntityWrapper<SeckillOrder>();
        Page<SeckillOrder> page = new Page<SeckillOrder>(pageNum, pageSize);

        List<SeckillOrder> pageInfoList = seckillOrderMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(seckillOrderMapper.selectPage(page, entity));

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
    public void add(SeckillOrder seckillOrder) {
        seckillOrderMapper.insert(seckillOrder);
    }


    /**
     * 修改
     */
    @Override
    public void update(SeckillOrder seckillOrder) {
        seckillOrderMapper.updateById(seckillOrder);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public SeckillOrder findOne(Long id) {
        return seckillOrderMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            seckillOrderMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(SeckillOrder seckillOrder, int pageNum, int pageSize) {
        //分页查询
        Wrapper<SeckillOrder> entity = new EntityWrapper<SeckillOrder>();

        if (seckillOrder != null) {
            if (seckillOrder.getUserId() != null && seckillOrder.getUserId().length() > 0) {
                entity.like("user_id", seckillOrder.getUserId());
                //criteria.andUserIdLike("%" + seckillOrder.getUserId() + "%");
            }
            if (seckillOrder.getSellerId() != null && seckillOrder.getSellerId().length() > 0) {
                entity.like("seller_id", seckillOrder.getSellerId());
                //criteria.andSellerIdLike("%" + seckillOrder.getSellerId() + "%");
            }
            if (seckillOrder.getStatus() != null && seckillOrder.getStatus().length() > 0) {
                entity.like("status", seckillOrder.getStatus());
                //criteria.andStatusLike("%" + seckillOrder.getStatus() + "%");
            }
            if (seckillOrder.getReceiverAddress() != null && seckillOrder.getReceiverAddress().length() > 0) {
                entity.like("receiver_address", seckillOrder.getReceiverAddress());
                //criteria.andReceiverAddressLike("%" + seckillOrder.getReceiverAddress() + "%");
            }
            if (seckillOrder.getReceiverMobile() != null && seckillOrder.getReceiverMobile().length() > 0) {
                entity.like("receiver_mobile", seckillOrder.getReceiverMobile());
                //criteria.andReceiverMobileLike("%" + seckillOrder.getReceiverMobile() + "%");
            }
            if (seckillOrder.getReceiver() != null && seckillOrder.getReceiver().length() > 0) {
                entity.like("receiver", seckillOrder.getReceiver());
                //criteria.andReceiverLike("%" + seckillOrder.getReceiver() + "%");
            }
            if (seckillOrder.getTransactionId() != null && seckillOrder.getTransactionId().length() > 0) {
                entity.like("transaction_id", seckillOrder.getTransactionId());
                //criteria.andTransactionIdLike("%" + seckillOrder.getTransactionId() + "%");
            }

        }

        Page<SeckillOrder> page = new Page<SeckillOrder>(pageNum, pageSize);

        List<SeckillOrder> pageInfoList = seckillOrderMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(seckillOrderMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
