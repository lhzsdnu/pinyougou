package com.pinyougou.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pinyougou.entity.Specification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@Mapper
public interface SpecificationMapper extends BaseMapper<Specification> {
    List<Map> selectOptionList();
}
