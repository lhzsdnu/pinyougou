package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.ContentCategory;
import com.pinyougou.mapper.ContentCategoryMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 内容分类 服务实现类
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
public class ContentCategoryServiceImpl extends ServiceImpl<ContentCategoryMapper, ContentCategory> implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    /**
     * 查询全部
     */
    @Override
    public List<ContentCategory> findAll() {
        return contentCategoryMapper.selectList(new EntityWrapper<ContentCategory>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<ContentCategory> entity = new EntityWrapper<ContentCategory>();
        Page<ContentCategory> page = new Page<ContentCategory>(pageNum, pageSize);

        List<ContentCategory> pageInfoList = contentCategoryMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(contentCategoryMapper.selectPage(page, entity));

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
    public void add(ContentCategory contentCategory) {
        contentCategoryMapper.insert(contentCategory);
    }


    /**
     * 修改
     */
    @Override
    public void update(ContentCategory contentCategory) {
        contentCategoryMapper.updateById(contentCategory);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public ContentCategory findOne(Long id) {
        return contentCategoryMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            contentCategoryMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(ContentCategory contentCategory, int pageNum, int pageSize) {

        //分页查询
        Wrapper<ContentCategory> entity = new EntityWrapper<ContentCategory>();

        if (contentCategory != null) {

            if (contentCategory.getName() != null && contentCategory.getName().length() > 0) {
                entity.like("name", contentCategory.getName());
                //criteria.andNameLike("%"+contentCategory.getName()+"%");
            }

        }

        Page<ContentCategory> page = new Page<ContentCategory>(pageNum, pageSize);

        List<ContentCategory> pageInfoList = contentCategoryMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(contentCategoryMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }
}
