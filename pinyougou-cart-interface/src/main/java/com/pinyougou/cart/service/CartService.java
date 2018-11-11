package com.pinyougou.cart.service;

import com.pinyougou.pojo.Cart;

import java.util.List;

/**
 * 购物车服务接口
 */
public interface CartService {
	/**
	 * 添加商品到购物车
	 */
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num );
}
