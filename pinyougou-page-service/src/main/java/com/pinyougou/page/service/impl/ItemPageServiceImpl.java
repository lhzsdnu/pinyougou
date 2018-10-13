package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.entity.Goods;
import com.pinyougou.entity.GoodsDesc;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.mapper.GoodsMapper;
import com.pinyougou.page.service.ItemPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service(
		version = "${demo.service.version}",
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsDescMapper goodsDescMapper;
		
	@Override
	public boolean genItemHtml(Long goodsId){				
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Map dataModel=new HashMap<>();
			//1.加载商品表数据
			Goods goods = goodsMapper.selectById(goodsId);
			dataModel.put("goods", goods);			
			//2.加载商品扩展表数据			
			GoodsDesc goodsDesc = goodsDescMapper.selectById(goodsId);
			dataModel.put("goodsDesc", goodsDesc);
			String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
			Writer out=new FileWriter(path+"/static/"+goodsId+".html");
			template.process(dataModel, out);
			out.close();
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}