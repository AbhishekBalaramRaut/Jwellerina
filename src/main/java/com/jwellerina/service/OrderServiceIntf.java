package com.jwellerina.service;

import java.util.Map;

import com.jwellerina.documents.Order;
import com.jwellerina.model.CustomerDto;

public interface OrderServiceIntf {

	public Map<String, Object> placeOrder(Order c, String token);
	public Map<String, Object>  getOrder(Integer orderId, String token);
	public Map<String, Object> getOrders(String token);
	public CustomerDto getProfile(String token);
	public String cancelOrder(Integer oId, String token);
	public String cancelSubOrder(Integer subOId, String token);
}
