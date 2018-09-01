package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.GoodsDesc;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.GoodsDescService;
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
public class GoodsDescServiceImpl extends ServiceImpl<GoodsDescMapper, GoodsDesc> implements GoodsDescService {

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    /**
     * 查询全部
     */
    @Override
    public List<GoodsDesc> findAll() {
        return goodsDescMapper.selectList(new EntityWrapper<GoodsDesc>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<GoodsDesc> entity = new EntityWrapper<GoodsDesc>();
        Page<GoodsDesc> page = new Page<GoodsDesc>(pageNum, pageSize);

        List<GoodsDesc> pageInfoList = goodsDescMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(goodsDescMapper.selectPage(page, entity));

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
    public void add(GoodsDesc goodsDesc) {
        goodsDescMapper.insert(goodsDesc);
    }


    /**
     * 修改
     */
    @Override
    public void update(GoodsDesc goodsDesc) {
        goodsDescMapper.updateById(goodsDesc);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public GoodsDesc findOne(Long id) {
        return goodsDescMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            goodsDescMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(GoodsDesc goodsDesc, int pageNum, int pageSize) {

        //分页查询
        Wrapper<GoodsDesc> entity = new EntityWrapper<GoodsDesc>();

        if (goodsDesc != null) {
            if (goodsDesc.getIntroduction() != null && goodsDesc.getIntroduction().length() > 0) {
                entity.like("introduction", goodsDesc.getIntroduction());
                //criteria.andIntroductionLike("%" + goodsDesc.getIntroduction() + "%");
            }
            if (goodsDesc.getSpecificationItems() != null && goodsDesc.getSpecificationItems().length() > 0) {
                entity.like("specification_items", goodsDesc.getSpecificationItems());
                //criteria.andSpecificationItemsLike("%" + goodsDesc.getSpecificationItems() + "%");
            }
            if (goodsDesc.getCustomAttributeItems() != null && goodsDesc.getCustomAttributeItems().length() > 0) {
                entity.like("custom_attribute_items", goodsDesc.getCustomAttributeItems());
                //criteria.andCustomAttributeItemsLike("%" + goodsDesc.getCustomAttributeItems() + "%");
            }
            if (goodsDesc.getItemImages() != null && goodsDesc.getItemImages().length() > 0) {
                entity.like("item_images", goodsDesc.getItemImages());
                //criteria.andItemImagesLike("%" + goodsDesc.getItemImages() + "%");
            }
            if (goodsDesc.getPackageList() != null && goodsDesc.getPackageList().length() > 0) {
                entity.like("package_list", goodsDesc.getPackageList());
                //criteria.andPackageListLike("%" + goodsDesc.getPackageList() + "%");
            }
            if (goodsDesc.getSaleService() != null && goodsDesc.getSaleService().length() > 0) {
                entity.like("sale_service", goodsDesc.getSaleService());
                //criteria.andSaleServiceLike("%" + goodsDesc.getSaleService() + "%");
            }

        }
        Page<GoodsDesc> page = new Page<GoodsDesc>(pageNum, pageSize);

        List<GoodsDesc> pageInfoList = goodsDescMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(goodsDescMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;

    }

}
