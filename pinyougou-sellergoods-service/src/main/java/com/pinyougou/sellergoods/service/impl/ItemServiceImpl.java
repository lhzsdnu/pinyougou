package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Item;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
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
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Item> findAll() {
        return itemMapper.selectList(new EntityWrapper<Item>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Item> entity = new EntityWrapper<Item>();
        Page<Item> page = new Page<Item>(pageNum, pageSize);

        List<Item> pageInfoList = itemMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(itemMapper.selectPage(page, entity));

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
    public void add(Item item) {
        itemMapper.insert(item);
    }


    /**
     * 修改
     */
    @Override
    public void update(Item item) {
        itemMapper.updateById(item);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Item findOne(Long id) {
        return itemMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            itemMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Item item, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Item> entity = new EntityWrapper<Item>();

        if (item != null) {
            if (item.getTitle() != null && item.getTitle().length() > 0) {
                entity.like("title", item.getTitle());
                //criteria.andTitleLike("%" + item.getTitle() + "%");
            }
            if (item.getSellPoint() != null && item.getSellPoint().length() > 0) {
                entity.like("sell_point", item.getSellPoint());
                //criteria.andSellPointLike("%" + item.getSellPoint() + "%");
            }
            if (item.getBarcode() != null && item.getBarcode().length() > 0) {
                entity.like("barcode", item.getBarcode());
                //criteria.andBarcodeLike("%" + item.getBarcode() + "%");
            }
            if (item.getImage() != null && item.getImage().length() > 0) {
                entity.like("image", item.getImage());
                //criteria.andImageLike("%" + item.getImage() + "%");
            }
            if (item.getStatus() != null && item.getStatus().length() > 0) {
                entity.like("status", item.getStatus());
                //criteria.andStatusLike("%" + item.getStatus() + "%");
            }
            if (item.getItemSn() != null && item.getItemSn().length() > 0) {
                entity.like("item_sn", item.getItemSn());
                //criteria.andItemSnLike("%" + item.getItemSn() + "%");
            }
            if (item.getIsDefault() != null && item.getIsDefault().length() > 0) {
                entity.like("is_default", item.getIsDefault());
                //criteria.andIsDefaultLike("%" + item.getIsDefault() + "%");
            }
            if (item.getSellerId() != null && item.getSellerId().length() > 0) {
                entity.like("seller_id", item.getSellerId());
                //criteria.andSellerIdLike("%" + item.getSellerId() + "%");
            }
            if (item.getCartThumbnail() != null && item.getCartThumbnail().length() > 0) {
                entity.like("cart_thumbnail", item.getCartThumbnail());
                //criteria.andCartThumbnailLike("%" + item.getCartThumbnail() + "%");
            }
            if (item.getCategory() != null && item.getCategory().length() > 0) {
                entity.like("category", item.getCategory());
                //criteria.andCategoryLike("%" + item.getCategory() + "%");
            }
            if (item.getBrand() != null && item.getBrand().length() > 0) {
                entity.like("brand", item.getBrand());
                //criteria.andBrandLike("%" + item.getBrand() + "%");
            }
            if (item.getSpec() != null && item.getSpec().length() > 0) {
                entity.like("spec", item.getSpec());
                //criteria.andSpecLike("%" + item.getSpec() + "%");
            }
            if (item.getSeller() != null && item.getSeller().length() > 0) {
                entity.like("seller", item.getSeller());
                //criteria.andSellerLike("%" + item.getSeller() + "%");
            }

        }

        Page<Item> page = new Page<Item>(pageNum, pageSize);

        List<Item> pageInfoList = itemMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(itemMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }
}
