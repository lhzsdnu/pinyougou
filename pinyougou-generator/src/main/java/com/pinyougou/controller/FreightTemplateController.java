package com.pinyougou.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.FreightTemplate;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.pojo.Result;
import com.pinyougou.service.FreightTemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@RestController
@RequestMapping("/freightTemplate")
public class FreightTemplateController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private FreightTemplateService freightTemplateService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<FreightTemplate> findAll() {
        return freightTemplateService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return freightTemplateService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param freightTemplate
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody FreightTemplate freightTemplate) {
        try {
            freightTemplateService.add(freightTemplate);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param freightTemplate
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody FreightTemplate freightTemplate) {
        try {
            freightTemplateService.update(freightTemplate);
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
    public FreightTemplate findOne(Long id) {
        return freightTemplateService.findOne(id);
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
            freightTemplateService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param freightTemplate
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody FreightTemplate freightTemplate, int page, int rows) {
        return freightTemplateService.findPage(freightTemplate, page, rows);
    }

}

