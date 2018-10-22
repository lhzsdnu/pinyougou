package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.User;
import com.pinyougou.mapper.UserMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.redis.RedisUtil;
import com.pinyougou.user.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Destination;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询全部
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectList(new EntityWrapper<User>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<User> entity = new EntityWrapper<User>();
        Page<User> page = new Page<User>(pageNum, pageSize);

        List<User> pageInfoList = userMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(userMapper.selectPage(page, entity));

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
    public void add(User user) {
        user.setCreated(new Date());//创建日期
        user.setUpdated(new Date());//修改日期
        String password = DigestUtils.md5Hex(user.getPassword());//对密码加密
        user.setPassword(password);
        userMapper.insert(user);
    }


    /**
     * 修改
     */
    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public User findOne(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            userMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(User user, int pageNum, int pageSize) {

        //分页查询
        Wrapper<User> entity = new EntityWrapper<User>();

        if (user != null) {
            if (user.getUsername() != null && user.getUsername().length() > 0) {
                entity.like("username", user.getUsername());
                //criteria.andUsernameLike("%" + user.getUsername() + "%");
            }
            if (user.getPassword() != null && user.getPassword().length() > 0) {
                entity.like("password", user.getPassword());
                //criteria.andPasswordLike("%" + user.getPassword() + "%");
            }
            if (user.getPhone() != null && user.getPhone().length() > 0) {
                entity.like("phone", user.getPhone());
                //criteria.andPhoneLike("%" + user.getPhone() + "%");
            }
            if (user.getEmail() != null && user.getEmail().length() > 0) {
                entity.like("email", user.getEmail());
                //criteria.andEmailLike("%" + user.getEmail() + "%");
            }
            if (user.getSourceType() != null && user.getSourceType().length() > 0) {
                entity.like("source_type", user.getSourceType());
                //criteria.andSourceTypeLike("%" + user.getSourceType() + "%");
            }
            if (user.getNickName() != null && user.getNickName().length() > 0) {
                entity.like("nick_name", user.getNickName());
                //criteria.andNickNameLike("%" + user.getNickName() + "%");
            }
            if (user.getName() != null && user.getName().length() > 0) {
                entity.like("name", user.getName());
                //criteria.andNameLike("%" + user.getName() + "%");
            }
            if (user.getStatus() != null && user.getStatus().length() > 0) {
                entity.like("status", user.getStatus());
                //criteria.andStatusLike("%" + user.getStatus() + "%");
            }
            if (user.getHeadPic() != null && user.getHeadPic().length() > 0) {
                entity.like("head_pic", user.getHeadPic());
                //criteria.andHeadPicLike("%" + user.getHeadPic() + "%");
            }
            if (user.getQq() != null && user.getQq().length() > 0) {
                entity.like("qq", user.getQq());
                //criteria.andQqLike("%" + user.getQq() + "%");
            }
            if (user.getIsMobileCheck() != null && user.getIsMobileCheck().length() > 0) {
                entity.like("is_mobile_check", user.getIsMobileCheck());
                //criteria.andIsMobileCheckLike("%" + user.getIsMobileCheck() + "%");
            }
            if (user.getIsEmailCheck() != null && user.getIsEmailCheck().length() > 0) {
                entity.like("is_email_check", user.getIsEmailCheck());
                //criteria.andIsEmailCheckLike("%" + user.getIsEmailCheck() + "%");
            }
            if (user.getSex() != null && user.getSex().length() > 0) {
                entity.like("sex", user.getSex());
                //criteria.andSexLike("%" + user.getSex() + "%");
            }

        }

        Page<User> page = new Page<User>(pageNum, pageSize);

        List<User> pageInfoList = userMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(userMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsTemplate;

    /**
     * 生成验证码
     */
    @Override
    public void createSmsCode(String phone){
        //生成6位随机数
        String code =  (long) (Math.random()*1000000)+"";
        System.out.println("验证码："+code);
        //存入缓存
        redisUtil.hset("smscode", phone,code);
        //发送到activeMQ	....
        Destination destination = new ActiveMQQueue("smsDestination");
        //发送消息，destination是发送到的队列，message是待发送的消息
        //jmsTemplate.convertAndSend(destination, message);
        Map<String, String> map=new HashMap<String, String>();
        map.put("phone",phone);
        map.put("code",code);
        jmsTemplate.convertAndSend(destination, map);
    }

    /**
     * 判断验证码是否正确
     */
    @Override
    public boolean  checkSmsCode(String phone,String code){
        //得到缓存中存储的验证码
        String sysCode = (String) redisUtil.hget("smscode", phone);
        if(sysCode==null){
            return false;
        }
        if(!sysCode.equals(code)){
            return false;
        }
        return true;
    }


}
