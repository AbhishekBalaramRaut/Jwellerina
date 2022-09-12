package com.jwellerina.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jwellerina.documents.Category;
import com.jwellerina.documents.Customer;
import com.jwellerina.documents.Item;
import com.jwellerina.model.CustomerDto;
import com.jwellerina.model.JwtResponse;
import com.jwellerina.repositories.CategoryRepository;
import com.jwellerina.repositories.ItemRepository;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;

@Service
public class ItemsService implements ItemsServiceIntf {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	private MongoTemplate mt;

	@Override
	public String addEditCategory(Category c) {
		Optional<Category> cat = null;
		
		if (c.getId() != null) {
			cat = categoryRepository.findById(c.getId());

			if (!cat.isPresent()) {
				return ErrorCodeConstants.INVALID_CATEGORY_ID;
			}
		}
		List<Item> itemList = new ArrayList<>();
		// c.setItems(list);
		if (c.getId() == null) {
			c.setId(generateCategoryId());
		}

		if (c.getItems() != null && c.getItems().size() > 0) {
			itemList = new ArrayList<>();
			List<Item> newList = new ArrayList<>();
			newList = (List<Item>) c.getItems();
			for (int i = 0; i < newList.size(); i++) {
				Item m = newList.get(i);
				if (m.getId() != null) {
					Optional<Item> k = itemRepository.findById(m.getId());

					if (k.isPresent()) {
						Item tobeInsertedItem = k.get();
						tobeInsertedItem.setCategoryId(c.getId());
						itemList.add(tobeInsertedItem);
					}

				}
			}

			c.setItems(itemList);

		}
		c = categoryRepository.save(c);

		for (Item ob : itemList) {
			if (ob.getCategoryId() != null) {
				ob.setCategoryId(c.getId());
				itemRepository.save(ob);
			}

		}
		return GeneralConstants.SUCCESS_CODE;
	}

	@Override
	public String addEditItem(Item c) {

		Optional<Item> cat = null;
		Item db = null;
		if (c.getId() != null) {
			cat = itemRepository.findById(c.getId());

			if (!cat.isPresent()) {
				return ErrorCodeConstants.INVALID_ITEM_ID;
			}
			db = cat.get();
		}
		// c.setItems(list);
		if (c.getId() == null) {
			c.setId(generateItemId());
		} else {
			if (!StringUtils.isEmpty(c.getName())) {
				db.setName(c.getName());
			}
			if (!StringUtils.isEmpty(c.getDescription())) {
				db.setDescription(c.getDescription());
			}
			if (!StringUtils.isEmpty(c.getImage())) {
				db.setImage(c.getImage());
			}
			if (c.getPrice() > 0) {
				db.setPrice(c.getPrice());
			}

		}

		c = itemRepository.save(c);

		return GeneralConstants.SUCCESS_CODE;
	}

	private Integer generateCategoryId() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		query.limit(1);
		Category maxEntity = mt.findOne(query, Category.class);
		if (maxEntity == null) {
			return 1;
		} else {
			return (maxEntity.getId() + 1);
		}
	}

	private Integer generateItemId() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		query.limit(1);
		Item maxEntity = mt.findOne(query, Item.class);
		if (maxEntity == null) {
			return 1;
		} else {
			return (maxEntity.getId() + 1);
		}
	}

	@Override
	public Map<String, Object> getItem(Integer itemId) {
		Map<String, Object> result = new HashMap<>();
		if (itemId == null) {
			result.put(GeneralConstants.CODE, ErrorCodeConstants.ITEM_ID_IS_REQUIRED);
			return result;
		}

		Optional<Item> cat = null;
		Item db = null;

		cat = itemRepository.findById(itemId);

		if (!cat.isPresent()) {
			result.put(GeneralConstants.CODE, ErrorCodeConstants.INVALID_ITEM_ID);
			return result;
		}
		result.put(GeneralConstants.CODE, GeneralConstants.SUCCESS_CODE);
		result.put(GeneralConstants.DATA, cat.get());

		return result;
	}

	@Override
	public Map<String, Object> getItems(Integer categoryId) {
		Map<String, Object> result = new HashMap<>();
		if (categoryId == null) {
			result.put(GeneralConstants.CODE, ErrorCodeConstants.CATEGORY_ID_IS_REQUIRED);
			return result;
		}

		Optional<Category> cat = null;
		Category db = null;

		cat = categoryRepository.findById(categoryId);

		if (!cat.isPresent()) {
			result.put(GeneralConstants.CODE, ErrorCodeConstants.INVALID_CATEGORY_ID);
			return result;
		}
		result.put(GeneralConstants.CODE, GeneralConstants.SUCCESS_CODE);
		result.put(GeneralConstants.DATA, (cat.get()).getItems());

		return result;
	}

	@Override
	public Map<String, Object> getCategories() {
		Map<String, Object> result = new HashMap<>();
		List<Category> list = categoryRepository.findAll();
		result.put(GeneralConstants.CODE, GeneralConstants.SUCCESS_CODE);
		result.put(GeneralConstants.DATA, list);

		return result;
	}

}
