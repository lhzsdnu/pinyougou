package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.entity.Content;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.redis.RedisUtil;
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

    @Autowired
    private RedisUtil redisUtil;


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
        //清除缓存
        redisUtil.hdel("content",String.valueOf(content.getCategoryId()));
    }


    /**
     * 修改
     */
    @Override
    public void update(Content content) {

        Long categoryId = contentMapper.selectById(content.getId()).getCategoryId();
        redisUtil.hdel("content",String.valueOf(categoryId));

        contentMapper.updateById(content);

        if(categoryId.longValue()!=content.getCategoryId().longValue()){
            redisUtil.hdel("content",String.valueOf(content.getCategoryId()));
        }


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
            Long categoryId = contentMapper.selectById(id).getCategoryId();
            redisUtil.hdel("content",String.valueOf(categoryId));
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

    @Override
    public List<Content> findByCategoryId(Long categoryId) {

        // categoryId  可能多个
        List<Content> contentList = (List<Content>) redisUtil.hget("content", String.valueOf(categoryId));

        if (contentList == null) {
            System.out.println("从数据库读取数据放入缓存");
            //根据广告分类ID查询广告列表
            Wrapper<Content> entity = new EntityWrapper<Content>();

            //广告分类ID
            entity.eq("category_id", categoryId);
            //开启状态（有效）
            entity.eq("status", "1");
            //排序,默认升序
            entity.orderBy("sort_order");

            contentList = contentMapper.selectList(entity);
            //存入缓存
            redisUtil.hset("content", String.valueOf(categoryId), contentList);

        } else {
            System.out.println("从缓存读取数据");
        }
        return contentList;
    }

}
