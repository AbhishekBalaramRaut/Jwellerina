package com.jwellerina.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.jwellerina.documents.Item;
import com.jwellerina.documents.Order;
import com.jwellerina.documents.OrderItem;
import com.jwellerina.model.CustomerDto;
import com.jwellerina.repositories.ItemRepository;
import com.jwellerina.repositories.OrderItemRepository;
import com.jwellerina.repositories.OrderRepository;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;
import com.jwellerina.utils.UtilityFunctions;

@Service
public class OrderService implements OrderServiceIntf {

	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UtilityFunctions utilityFunctions;
	
	@Autowired
	private MongoTemplate mt;
	
	@Override
	public String placeOrder(Order c, String token) {
		CustomerDto cust = utilityFunctions.getCurrentProfile(token);
		c.setMobile(cust.getMobile());
		List<OrderItem> oItems = (List<OrderItem>) c.getItems();
		Optional<Item> cat = null;
		
		for(OrderItem ot: oItems) {
			if(ot.getQuantity() == null) {
				return ErrorCodeConstants.INVALID_QUANTITY;
			}
			Item p = ot.getItem();
			
			if(p.getId() == null) {
				return ErrorCodeConstants.ITEM_ID_IS_REQUIRED;
			}		

			cat = itemRepository.findById(p.getId());

			if (!cat.isPresent()) {
				return ErrorCodeConstants.INVALID_ITEM_ID;
			}
			ot.setItem(cat.get());
			ot.setId(generateOrderItemId());
			ot.setStatus(GeneralConstants.STATUS_ORDERED);
			orderItemRepository.save(ot);
		}
		
		c.setItems(oItems);
		c.setStatus(GeneralConstants.STATUS_ORDERED);
		c.setId(generateOrderId());
		c.setOrderDate(new Timestamp(System.currentTimeMillis()));
		c.setPayment(false);
		orderRepository.save(c);
		return GeneralConstants.SUCCESS_CODE;
	}

	private Integer generateOrderItemId() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		query.limit(1);
		OrderItem maxEntity = mt.findOne(query, OrderItem.class);
		if (maxEntity == null) {
			return 1;
		} else {
			return (maxEntity.getId() + 1);
		}
	}
	
	private Integer generateOrderId() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		query.limit(1);
		Order maxEntity = mt.findOne(query, Order.class);
		if (maxEntity == null) {
			return 1;
		} else {
			return (maxEntity.getId() + 1);
		}
	}

	@Override
	public Map<String, Object> getOrder(Integer orderId, String token) {
		Map<String, Object> result = new HashMap<>();
		CustomerDto cust = utilityFunctions.getCurrentProfile(token);
		Order order = null;
		
		order = orderRepository.findByIdAndMobile(orderId, cust.getMobile());

		if (order == null) {
			result.put(GeneralConstants.CODE, ErrorCodeConstants.INVALID_ORDER);
			return result;
		}
		
		
		result.put(GeneralConstants.CODE, GeneralConstants.SUCCESS_CODE);
		result.put(GeneralConstants.DATA, order);

		return result;
	}

	@Override
	public Map<String, Object> getOrders(String token) {
		Map<String, Object> result = new HashMap<>();
		CustomerDto cust = utilityFunctions.getCurrentProfile(token);
		List<Order> cat = null;
		
		cat = orderRepository.findAllByMobile(cust.getMobile());

		if (cat == null) {
			result.put(GeneralConstants.CODE, ErrorCodeConstants.INVALID_ORDER);
			return result;
		}
		
		result.put(GeneralConstants.CODE, GeneralConstants.SUCCESS_CODE);
		result.put(GeneralConstants.DATA, cat);

		return result;
	}

	@Override
	public String cancelOrder(Integer oId, String token) {
		CustomerDto cust = utilityFunctions.getCurrentProfile(token);
		Order cat = null;
		
		cat = orderRepository.findByIdAndMobile(oId, cust.getMobile());

		if (cat == null) {
			return ErrorCodeConstants.INVALID_ORDER;
		}
		List<OrderItem> items =  (List<OrderItem>) cat.getItems();
		for(OrderItem it: items) {
			it.setStatus(GeneralConstants.STATUS_CANCELLED);
			orderItemRepository.save(it);
		}
		cat.setStatus(GeneralConstants.STATUS_CANCELLED);
		orderRepository.save(cat);
		return GeneralConstants.SUCCESS_CODE;
	}

	@Override
	public String cancelSubOrder(Integer subOId, String token) {
		CustomerDto cust = utilityFunctions.getCurrentProfile(token);
		List<Order> cat = null;
		
		cat = orderRepository.findAllByMobile(cust.getMobile());

		if (cat == null) {
			return ErrorCodeConstants.INVALID_ORDER;
		}
		boolean found = false;
		List<OrderItem> items = null;
		for(Order o: cat) {
			items = (List<OrderItem>) o.getItems();
			
			for(OrderItem ot: items) {
				if(ot.getId() == subOId) {
					found = true;
					ot.setStatus(GeneralConstants.STATUS_CANCELLED);
					orderItemRepository.save(ot);
				}
			}
		}
		if(found) {
			return GeneralConstants.SUCCESS_CODE;
		} else {
			return ErrorCodeConstants.INVALID_ITEM_ID;
		}
		
	}
}
