package com.pinyougou.solrutil.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pinyougou.entity.Item;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.CopyItem;
import com.pinyougou.solrutil.config.ChangeToPinYinJP;
import com.pinyougou.solrutil.config.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SolrUtil {

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
			copyItem.setBrand(item.getBrand());
			copyItem.setCategory(item.getCategory());
			copyItem.setGoodsId(item.getGoodsId());
			copyItem.setImage(item.getImage());
			copyItem.setPrice(item.getPrice().toString());
			copyItem.setSeller(item.getSeller());
			copyItem.setTitle(item.getTitle());

			//将spec字段中的json字符串转换为map
			Map<String,String> specMap=JSON.parseObject(item.getSpec(),Map.class);
			//changeToPinYinJP.changeToTonePinYinNoSpace();
			Map<String, String> map = new HashMap<String, String>();
			for (Map.Entry<String, String> entry : specMap.entrySet()) {
				String key=changeToPinYinJP.changeToTonePinYinNoSpace(entry.getKey());
				String value=entry.getValue();
				map.put(key,value);
			}
			//给带注解的字段赋值
			copyItem.setSpecMap(map);

			copyItemList.add(copyItem);
		}

		musicRepository.saveAll(copyItemList);
	}
}
