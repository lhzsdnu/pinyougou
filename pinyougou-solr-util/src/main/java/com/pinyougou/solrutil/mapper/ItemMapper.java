package com.pinyougou.solrutil.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pinyougou.solrutil.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@Mapper
@Repository
public interface ItemMapper extends BaseMapper<Item> {

}
