package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.entity.Item;
import com.pinyougou.entity.OrderItem;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(
		version = "${demo.service.version}",
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
public class CartServiceImpl implements CartService {

	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
	
		//1.根据商品SKU ID查询SKU商品信息
		Item item = itemMapper.selectById(itemId);
		if(item==null){
			throw new RuntimeException("商品不存在");
		}
		if(!item.getStatus().equals("1")){
			throw new RuntimeException("商品状态无效");
		}
		
		//2.获取商家ID		
		String sellerId = item.getSellerId();
		
		//3.根据商家ID判断购物车列表中是否存在该商家的购物车		
		Cart cart = searchCartBySellerId(cartList,sellerId);
		
		//4.如果购物车列表中不存在该商家的购物车
		if(cart==null){		
			
			//4.1 新建购物车对象 ，
			cart=new Cart();
			cart.setSellerId(sellerId);
			cart.setSellerName(item.getSeller());						
			OrderItem orderItem = createOrderItem(item,num);
			List orderItemList=new ArrayList();
            orderItemList.add(orderItem);
			cart.setOrderItemList(orderItemList);

			//4.2将购物车对象添加到购物车列表
			cartList.add(cart);
			
		}else{
			//5.如果购物车列表中存在该商家的购物车			
			// 判断购物车明细列表中是否存在该商品
			OrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(),itemId);
						
			if(orderItem==null){
				//5.1. 如果没有，新增购物车明细				
				orderItem=createOrderItem(item,num);
				cart.getOrderItemList().add(orderItem);
			}else{
				//5.2. 如果有，在原购物车明细上添加数量，更改金额
				orderItem.setNum(orderItem.getNum()+num);			
				orderItem.setTotalFee(new BigDecimal(orderItem.getNum()*orderItem.getPrice().doubleValue())  );
				//如果数量操作后小于等于0，则移除
				if(orderItem.getNum()<=0){
					cart.getOrderItemList().remove(orderItem);//移除购物车明细	
				}
				//如果移除后cart的明细数量为0，则将cart移除
				if(cart.getOrderItemList().size()==0){
					cartList.remove(cart);
				}
			}			
		}			
		return cartList;
	}
	
	
	/**
	 * 根据商家ID查询购物车对象
	 * @param cartList
	 * @param sellerId
	 */
	private Cart searchCartBySellerId(List<Cart> cartList, String sellerId){
		for(Cart cart:cartList){
			if(cart.getSellerId().equals(sellerId)){
				return cart;
			}		
		}
		return null;
	}
	
	/**
	 * 根据商品明细ID查询
	 * @param orderItemList
	 * @param itemId
	 */
	private OrderItem searchOrderItemByItemId(List<OrderItem> orderItemList ,Long itemId ){
		for(OrderItem orderItem :orderItemList){
			if(orderItem.getItemId().longValue()==itemId.longValue()){
				return orderItem;				
			}			
		}
		return null;
	}
	
	/**
	 * 创建订单明细
	 * @param item
	 * @param num
	 * @return
	 */
	private OrderItem createOrderItem(Item item,Integer num){
		if(num<=0){
			throw new RuntimeException("数量非法");
		}
		
		OrderItem orderItem=new OrderItem();
		orderItem.setGoodsId(item.getGoodsId());
		orderItem.setItemId(item.getId());
		orderItem.setNum(num);
		orderItem.setPicPath(item.getImage());
		orderItem.setPrice(item.getPrice());
		orderItem.setSellerId(item.getSellerId());
		orderItem.setTitle(item.getTitle());
		orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
		return orderItem;
	}
}
