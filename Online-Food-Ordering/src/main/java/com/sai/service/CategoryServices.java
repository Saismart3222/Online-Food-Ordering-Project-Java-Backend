package com.sai.service;

import com.sai.model.Category;

import java.util.List;

public interface CategoryServices {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long id) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
