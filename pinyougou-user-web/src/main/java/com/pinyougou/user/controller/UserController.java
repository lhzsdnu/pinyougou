package com.pinyougou.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.User;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.pojo.Result;
import com.pinyougou.user.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private UserService userService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return userService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param user
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody User user,String smscode) {
        try {
            boolean checkSmsCode = userService.checkSmsCode(user.getPhone(), smscode);
            if(checkSmsCode==false){
                return new Result(false, "验证码输入错误！");
            }
            userService.add(user);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 发送短信验证码
     * @param phone
     */
    @RequestMapping("/sendCode")
    public Result sendCode(String phone){
        try {
            userService.createSmsCode(phone);//生成验证码
            return new Result(true, "验证码发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "验证码发送失败");
        }
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody User user) {
        try {
            userService.update(user);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public User findOne(Long id) {
        return userService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            userService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param user
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody User user, int page, int rows) {
        return userService.findPage(user, page, rows);
    }

}

