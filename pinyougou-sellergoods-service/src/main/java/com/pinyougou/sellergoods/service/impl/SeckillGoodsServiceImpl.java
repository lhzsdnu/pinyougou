package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.SeckillGoods;
import com.pinyougou.mapper.SeckillGoodsMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /**
     * 查询全部
     */
    @Override
    public List<SeckillGoods> findAll() {
        return seckillGoodsMapper.selectList(new EntityWrapper<SeckillGoods>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<SeckillGoods> entity = new EntityWrapper<SeckillGoods>();
        Page<SeckillGoods> page = new Page<SeckillGoods>(pageNum, pageSize);

        List<SeckillGoods> pageInfoList = seckillGoodsMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(seckillGoodsMapper.selectPage(page, entity));

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
    public void add(SeckillGoods seckillGoods) {
        seckillGoodsMapper.insert(seckillGoods);
    }


    /**
     * 修改
     */
    @Override
    public void update(SeckillGoods seckillGoods) {
        seckillGoodsMapper.updateById(seckillGoods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public SeckillGoods findOne(Long id) {
        return seckillGoodsMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            seckillGoodsMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(SeckillGoods seckillGoods, int pageNum, int pageSize) {

        //分页查询
        Wrapper<SeckillGoods> entity = new EntityWrapper<SeckillGoods>();

        if (seckillGoods != null) {
            if (seckillGoods.getTitle() != null && seckillGoods.getTitle().length() > 0) {
                entity.like("title", seckillGoods.getTitle());
                //criteria.andTitleLike("%" + seckillGoods.getTitle() + "%");
            }
            if (seckillGoods.getSmallPic() != null && seckillGoods.getSmallPic().length() > 0) {
                entity.like("small_pic", seckillGoods.getSmallPic());
                //criteria.andSmallPicLike("%" + seckillGoods.getSmallPic() + "%");
            }
            if (seckillGoods.getSellerId() != null && seckillGoods.getSellerId().length() > 0) {
                entity.like("seller_id", seckillGoods.getSellerId());
                //criteria.andSellerIdLike("%" + seckillGoods.getSellerId() + "%");
            }
            if (seckillGoods.getStatus() != null && seckillGoods.getStatus().length() > 0) {
                entity.like("status", seckillGoods.getStatus());
                //criteria.andStatusLike("%" + seckillGoods.getStatus() + "%");
            }
            if (seckillGoods.getIntroduction() != null && seckillGoods.getIntroduction().length() > 0) {
                entity.like("introduction", seckillGoods.getIntroduction());
                //criteria.andIntroductionLike("%" + seckillGoods.getIntroduction() + "%");
            }

        }

        Page<SeckillGoods> page = new Page<SeckillGoods>(pageNum, pageSize);

        List<SeckillGoods> pageInfoList = seckillGoodsMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(seckillGoodsMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
