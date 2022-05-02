package com.jwellerina.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwellerina.documents.Category;
import com.jwellerina.documents.Item;
import com.jwellerina.model.ServerResponse;
import com.jwellerina.service.ItemsService;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;
import com.jwellerina.utils.UtilityFunctions;

@RestController
public class AdminController {


	@Autowired
	UtilityFunctions utilityFunctions;
	
	@Autowired
	ItemsService itemsService;
	
	@RequestMapping(value = "/addEditCategory", method = RequestMethod.POST)
	public ServerResponse addEditCategory(@RequestBody Category c) throws Exception {
	
		String code = null;
		
		try {
			code = itemsService.addEditCategory(c);
		
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_ADD_CATEGORY,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,null);
	}
	
	@RequestMapping(value = "/addEditItem", method = RequestMethod.POST)
	public ServerResponse addEditItem(@RequestBody Item c) throws Exception {

		String code = null;
		
		try {
			code = itemsService.addEditItem(c);
		
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_ADD_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE, null);
	}
	
	@RequestMapping(value = "/items/{categoryId}", method = RequestMethod.GET)
	public ServerResponse getItems(@PathVariable Integer categoryId) throws Exception {

		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = itemsService.getItems(categoryId);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
	
	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
	public ServerResponse getItem(@PathVariable Integer itemId) throws Exception {

		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = itemsService.getItem(itemId);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ServerResponse getAllCategories() throws Exception {

		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = itemsService.getCategories();
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
}
