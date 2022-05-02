package com.jwellerina.service;

import java.util.Map;

import com.jwellerina.documents.Order;

public interface OrderServiceIntf {

	public String placeOrder(Order c, String token);
	public Map<String, Object>  getOrder(Integer orderId, String token);
	public Map<String, Object> getOrders(String token);
	public String cancelOrder(Integer oId, String token);
	public String cancelSubOrder(Integer subOId, String token);
}
