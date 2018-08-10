package com.pinyougou.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pinyougou.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
