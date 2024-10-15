package com.prashcode.dreamshop.service.category;

import com.prashcode.dreamshop.exceptions.AlreadyExistsException;
import com.prashcode.dreamshop.model.Category;
import com.prashcode.dreamshop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.prashcode.dreamshop.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInft{

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return List.of();
    }

    /*
    Why we use of_nullable and of answer is present below in stackoverflow website
    url: https://stackoverflow.com/questions/31696485/why-use-optional-of-over-optional-ofnullable
     */
    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName(    )))
                .map( categoryRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + "Already exits"));
    }
    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category Not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository :: delete, () -> {
            throw new ResourceNotFoundException("Category not found");
        });
    }
}
