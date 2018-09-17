package com.pinyougou.solrutil.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pinyougou.solrutil.config.ChangeToPinYinJP;
import com.pinyougou.solrutil.config.MusicRepository;
import com.pinyougou.solrutil.entity.Item;
import com.pinyougou.solrutil.mapper.ItemMapper;
import com.pinyougou.solrutil.pojo.CopyItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SolrUtil{

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private MusicRepository musicRepository;

	@Autowired
	private ChangeToPinYinJP changeToPinYinJP;

	@RequestMapping("deleteAll")
	public void deleteALLItemData(){
		musicRepository.deleteAll();
	}

	/**
	 * 导入商品数据
	 */
	@RequestMapping("import")
	public void importItemData(){
		Wrapper<Item> entity = new EntityWrapper<Item>();
		//已审核
		entity.eq("status","1");
		List<Item> itemList = itemMapper.selectList(entity);
		List<CopyItem> copyItemList=new ArrayList<CopyItem>();
		for(Item item:itemList){
			CopyItem copyItem=new CopyItem();
			copyItem.setId(String.valueOf(item.getId()));
			copyItem.setItem_brand(item.getBrand());
			copyItem.setItem_category(item.getCategory());
			copyItem.setItem_goodsId(item.getGoodsId());
			copyItem.setItem_image(item.getImage());
			copyItem.setItem_price(item.getPrice().toString());
			copyItem.setItem_seller(item.getSeller());
			copyItem.setItem_title(item.getTitle());

			//将spec字段中的json字符串转换为map
			Map<String,String> specMap=JSON.parseObject(item.getSpec(),Map.class);
			//changeToPinYinJP.changeToTonePinYinNoSpace();
			Map<String, String> map = new HashMap<String, String>();
			for (Map.Entry<String, String> entry : specMap.entrySet()) {
				String key=changeToPinYinJP.changeToTonePinYinNoSpace(entry.getKey());
				String value=entry.getValue();
				map.put(key,value);
			}

			System.out.println("@@@@@@@"+map);
			//给带注解的字段赋值
			copyItem.setSpecMap(map);
			copyItemList.add(copyItem);
		}

		musicRepository.saveAll(copyItemList);
	}
}
