package com.pinyougou.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.entity.User;
import com.pinyougou.pojo.PageResult;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
public interface UserService extends IService<User> {
    /**
     * 返回全部列表
     *
     * @return
     */
    public List<User> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(User user);


    /**
     * 修改
     */
    public void update(User user);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public User findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(User user, int pageNum, int pageSize);


    /**
     * 生成验证码
     */
    public void createSmsCode(String phone);

    /**
     * 判断短信验证码是否存在
     * @param phone
     */
    public boolean  checkSmsCode(String phone,String code);


}
