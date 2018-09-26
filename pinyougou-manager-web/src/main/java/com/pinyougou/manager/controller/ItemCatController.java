package com.pinyougou.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.ItemCat;
import com.pinyougou.entity.MyItemCat;
import com.pinyougou.entity.MyTypeTemplate;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.pojo.Result;
import com.pinyougou.sellergoods.service.ItemCatService;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private ItemCatService itemCatService;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private TypeTemplateService typeTemplateService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<ItemCat> findAll() {
        return itemCatService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return itemCatService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param itemCat
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody ItemCat itemCat) {
        try {
            itemCatService.add(itemCat);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param itemCat
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody ItemCat itemCat) {
        try {
            itemCatService.update(itemCat);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 根据id和text组装json字符串
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public MyItemCat findOne(Long id) {
        String text=typeTemplateService.findOne(itemCatService.findOne(id).getTypeId()).getName();
        MyTypeTemplate typeTemplate=new MyTypeTemplate(itemCatService.findOne(id).getTypeId().toString(),text);
        MyItemCat itemCat=new MyItemCat(id,itemCatService.findOne(id).getName(),typeTemplate);
        return  itemCat;

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
            String str=itemCatService.delete(ids);
            System.out.println(str);
            if(str!=null&&!"".equals(str)){
                return new Result(false, str);
            }else{
                return new Result(true, "删除成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param itemCat
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody ItemCat itemCat, int page, int rows) {
        return itemCatService.findPage(itemCat, page, rows);
    }

    /**
     * 根据上级ID查询列表
     * @param parentId
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }

}

