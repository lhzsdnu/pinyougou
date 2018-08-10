package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Content;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.ContentService;
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
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Content> findAll() {
        return contentMapper.selectList(new EntityWrapper<Content>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Content> entity = new EntityWrapper<Content>();
        Page<Content> page = new Page<Content>(pageNum, pageSize);

        List<Content> pageInfoList = contentMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(contentMapper.selectPage(page, entity));

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
    public void add(Content content) {
        contentMapper.insert(content);
    }


    /**
     * 修改
     */
    @Override
    public void update(Content content) {
        contentMapper.updateById(content);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Content findOne(Long id) {
        return contentMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            contentMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Content content, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Content> entity = new EntityWrapper<Content>();

        if (content != null) {

            if (content.getTitle() != null && content.getTitle().length() > 0) {
                entity.like("title", content.getTitle());
                //criteria.andTitleLike("%"+content.getTitle()+"%");
            }
            if (content.getUrl() != null && content.getUrl().length() > 0) {
                entity.like("url", content.getUrl());
                //criteria.andUrlLike("%"+content.getUrl()+"%");
            }
            if (content.getPic() != null && content.getPic().length() > 0) {
                entity.like("pic", content.getPic());
                //criteria.andPicLike("%"+content.getPic()+"%");
            }
            if (content.getStatus() != null && content.getStatus().length() > 0) {
                entity.like("status", content.getStatus());
                //criteria.andStatusLike("%"+content.getStatus()+"%");
            }


        }

        Page<Content> page = new Page<Content>(pageNum, pageSize);

        List<Content> pageInfoList = contentMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(contentMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }
}
