package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.PayLog;
import com.pinyougou.mapper.PayLogMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.PayLogService;
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
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private PayLogMapper payLogMapper;

    /**
     * 查询全部
     */
    @Override
    public List<PayLog> findAll() {
        return payLogMapper.selectList(new EntityWrapper<PayLog>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<PayLog> entity = new EntityWrapper<PayLog>();
        Page<PayLog> page = new Page<PayLog>(pageNum, pageSize);

        List<PayLog> pageInfoList = payLogMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(payLogMapper.selectPage(page, entity));

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
    public void add(PayLog payLog) {
        payLogMapper.insert(payLog);
    }


    /**
     * 修改
     */
    @Override
    public void update(PayLog payLog) {
        payLogMapper.updateById(payLog);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public PayLog findOne(Long id) {
        return payLogMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            payLogMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(PayLog payLog, int pageNum, int pageSize) {

        //分页查询
        Wrapper<PayLog> entity = new EntityWrapper<PayLog>();

        if (payLog != null) {
            if (payLog.getOutTradeNo() != null && payLog.getOutTradeNo().length() > 0) {
                entity.like("out_trade_no", payLog.getOutTradeNo());
                //criteria.andOutTradeNoLike("%" + payLog.getOutTradeNo() + "%");
            }
            if (payLog.getUserId() != null && payLog.getUserId().length() > 0) {
                entity.like("user_id", payLog.getUserId());
                //criteria.andUserIdLike("%" + payLog.getUserId() + "%");
            }
            if (payLog.getTransactionId() != null && payLog.getTransactionId().length() > 0) {
                entity.like("transaction_id", payLog.getTransactionId());
                //criteria.andTransactionIdLike("%" + payLog.getTransactionId() + "%");
            }
            if (payLog.getTradeState() != null && payLog.getTradeState().length() > 0) {
                entity.like("trade_state", payLog.getTradeState());
                //criteria.andTradeStateLike("%" + payLog.getTradeState() + "%");
            }
            if (payLog.getOrderList() != null && payLog.getOrderList().length() > 0) {
                entity.like("order_list", payLog.getOrderList());
                //criteria.andOrderListLike("%" + payLog.getOrderList() + "%");
            }
            if (payLog.getPayType() != null && payLog.getPayType().length() > 0) {
                entity.like("pay_type", payLog.getPayType());
                //criteria.andPayTypeLike("%" + payLog.getPayType() + "%");
            }

        }

        Page<PayLog> page = new Page<PayLog>(pageNum, pageSize);

        List<PayLog> pageInfoList = payLogMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(payLogMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
