package com.jwellerina.service;

import java.util.Map;

import com.jwellerina.documents.Category;
import com.jwellerina.documents.Item;

public interface ItemsServiceIntf {

	public String addEditCategory(Category c);
	public String addEditItem(Item c);
	public Map<String, Object>  getItem(Integer itemId);
	public Map<String, Object> getItems(Integer categoryId);
	public Map<String, Object> getCategories();
}
