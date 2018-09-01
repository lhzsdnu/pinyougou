package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.*;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.grouppojo.TbGoods;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private SellerMapper sellerMapper;


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
    public void add(TbGoods tbGoods) {

        tbGoods.getGoods().setAuditStatus("0");//设置未申请状态
        goodsMapper.insert(tbGoods.getGoods());
        tbGoods.getGoodsDesc().setGoodsId(tbGoods.getGoods().getId());//设置ID
        goodsDescMapper.insert(tbGoods.getGoodsDesc());//插入商品扩展数据

        saveItemList(tbGoods);
    }


    private void setItemValus(TbGoods goods, Item item) {
        item.setGoodsId(goods.getGoods().getId());//商品SPU编号
        item.setSellerId(goods.getGoods().getSellerId());//商家编号
        item.setCategoryId(goods.getGoods().getCategory3Id());//商品分类编号（3级）
        item.setCreateTime(new Date());//创建日期
        item.setUpdateTime(new Date());//修改日期

        //品牌名称
        Brand brand = brandMapper.selectById(goods.getGoods().getBrandId());
        item.setBrand(brand.getName());
        //分类名称
        ItemCat itemCat = itemCatMapper.selectById(goods.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());

        //商家名称
        Seller seller = sellerMapper.selectById(goods.getGoods().getSellerId());
        item.setSeller(seller.getNickName());

        //图片地址（取spu的第一个图片）
        List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (imageList.size() > 0) {
            item.setImage((String) imageList.get(0).get("url"));
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(TbGoods tbGoods) {
        //设置未申请状态:如果是经过修改的商品，需要重新设置状态
        tbGoods.getGoods().setAuditStatus("0");
        goodsMapper.updateById(tbGoods.getGoods());//保存商品表
        goodsDescMapper.updateById(tbGoods.getGoodsDesc());//保存商品扩展表

        //删除原有的sku列表数据
        Wrapper<Item> entity = new EntityWrapper<Item>();
        entity.eq("goods_id", tbGoods.getGoods().getId());//查询条件：商品ID
        itemMapper.delete(entity);
        //添加新的sku列表数据
        saveItemList(tbGoods);//插入商品SKU列表数据
    }

    /**
     * 插入SKU列表数据
     */
    private void saveItemList(TbGoods tbGoods) {
        if ("1".equals(tbGoods.getGoods().getIsEnableSpec())) {
            for (Item item : tbGoods.getItemList()) {
                //标题
                String title = tbGoods.getGoods().getGoodsName();
                Map<String, Object> specMap = JSON.parseObject(item.getSpec());
                for (String key : specMap.keySet()) {
                    title += " " + specMap.get(key);
                }
                item.setTitle(title);
                setItemValus(tbGoods, item);
                itemMapper.insert(item);
            }
        } else {
            Item item = new Item();
            item.setTitle(tbGoods.getGoods().getGoodsName());//商品KPU+规格描述串作为SKU名称
            item.setPrice(tbGoods.getGoods().getPrice());//价格
            item.setStatus("1");//状态
            item.setIsDefault("1");//是否默认
            item.setNum(99999);//库存数量
            item.setSpec("{}");
            setItemValus(tbGoods, item);
            itemMapper.insert(item);
        }
    }


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbGoods findOne(Long id) {
        TbGoods tbGoods = new TbGoods();
        Goods goods = goodsMapper.selectById(id);
        tbGoods.setGoods(goods);
        GoodsDesc tbGoodsDesc = goodsDescMapper.selectById(id);
        tbGoods.setGoodsDesc(tbGoodsDesc);

        //查询SKU商品列表
        Wrapper<Item> entity = new EntityWrapper<Item>();
        entity.eq("goods_id", id);//查询条件：商品ID
        List<Item> itemList = itemMapper.selectList(entity);
        tbGoods.setItemList(itemList);


        return tbGoods;
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
                entity.eq("seller_id", goods.getSellerId());
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
