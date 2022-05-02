package com.jwellerina.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jwellerina.documents.Order;
import com.jwellerina.model.CustomerDto;
import com.jwellerina.model.ServerResponse;
import com.jwellerina.service.OrderService;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;
import com.jwellerina.utils.UtilityFunctions;

@RestController
public class JwelleryController {


	@Autowired
	UtilityFunctions utilityFunctions;
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/order")
	public ServerResponse placeOrder(@RequestBody Order o,
			@RequestHeader("accessToken") String token) {
		String code = null;
		
		try {
			code = orderService.placeOrder(o, token);
		
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_PLACE_ORDER,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE, null);
	}
	
	@GetMapping("/order/{orderId}")
	public ServerResponse getOrder(@PathVariable Integer orderId,
			@RequestHeader("accessToken") String token) {
	
		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson =  orderService.getOrder(orderId, token);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ORDER,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}

	@GetMapping("/order")
	public ServerResponse getOrders(@RequestHeader("accessToken") String token) {
	
		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson =  orderService.getOrders(token);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ORDERS,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
	
	@DeleteMapping("/order/{orderId}")
	public ServerResponse cancelOrder(@PathVariable Integer orderId,
			@RequestHeader("accessToken") String token) {
			
		String code = null;
		
		try {
			code =  orderService.cancelOrder(orderId,token);
			
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_CANCEL_ORDERS,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,null);
	}
	
	@DeleteMapping("/order/sub/{subOrderId}")
	public ServerResponse cancelSubOrder(@PathVariable Integer subOrderId,
			@RequestHeader("accessToken") String token) {
			
		String code = null;
		
		try {
			code =  orderService.cancelSubOrder(subOrderId,token);
			
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_CANCEL_ORDERS,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,null);
	}
}
