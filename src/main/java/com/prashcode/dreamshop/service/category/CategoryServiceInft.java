package com.prashcode.dreamshop.service.category;

import com.prashcode.dreamshop.model.Category;

import java.util.List;

public interface CategoryServiceInft {

    //get category by id
    Category getCategoryById(Long id);
    // get category by name
    Category getCategoryByName(String name);
    // get all category
    List<Category> getAllCategory();
    //add category
    Category addCategory(Category category);
    //update category
    Category updateCategory(Category category, Long id);
    //delete category
    void deleteCategoryById(Long id);
}
