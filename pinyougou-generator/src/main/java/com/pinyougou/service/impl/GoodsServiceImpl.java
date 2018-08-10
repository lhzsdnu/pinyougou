package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Goods;
import com.pinyougou.mapper.GoodsMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.GoodsService;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectList(new EntityWrapper<Goods>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Goods> entity = new EntityWrapper<Goods>();
        Page<Goods> page = new Page<Goods>(pageNum, pageSize);

        List<Goods> pageInfoList = goodsMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(goodsMapper.selectPage(page, entity));

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
    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }


    /**
     * 修改
     */
    @Override
    public void update(Goods goods) {
        goodsMapper.updateById(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findOne(Long id) {
        return goodsMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            goodsMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Goods goods, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Goods> entity = new EntityWrapper<Goods>();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                entity.like("seller_id", goods.getSellerId());
                //criteria.andSellerIdLike("%" + goods.getSellerId() + "%");
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                entity.like("goods_name", goods.getGoodsName());
                //criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                entity.like("audit_status", goods.getAuditStatus());
                //criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                entity.like("is_marketable", goods.getIsMarketable());
                //criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                entity.like("caption", goods.getCaption());
                //criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                entity.like("small_pic", goods.getSmallPic());
                //criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                entity.like("is_enable_spec", goods.getIsEnableSpec());
                //criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                entity.like("is_delete", goods.getIsDelete());
                //criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<Goods> page = new Page<Goods>(pageNum, pageSize);

        List<Goods> pageInfoList = goodsMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(goodsMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
