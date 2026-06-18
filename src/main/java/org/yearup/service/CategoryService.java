package org.yearup.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        // Find all categories in the database
        return categoryRepository.findAll();
    }

    public Category getById(int categoryId) {
        // Try to find the category. If it doesn't exist, throw a 404 Not Found error
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."));
    }

    public Category create(Category category) {
        // Save the new category to the database
        return categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category) {
        // Make sure the ID on the object matches the ID in the URL
        category.setCategoryId(categoryId);
        // Save overwrites the existing record if the ID already exists
        return categoryRepository.save(category);
    }

    public void delete(int categoryId) {
        // Delete the category by its ID
        categoryRepository.deleteById(categoryId);
    }
}