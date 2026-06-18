package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

@RestController // Makes this a REST API controller
@RequestMapping("/categories") // Maps the URL to http://localhost:8080/categories
@CrossOrigin // Allows front-end apps (like React/Vue) to talk to this API
public class CategoriesController {

    private CategoryService categoryService;
    private ProductService productService;

    @Autowired // This automatically injects the services we need
    public CategoriesController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("permitAll()") // Anyone can view categories
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id) {
        return categoryService.getById(id);
    }

    @GetMapping("/{categoryId}/products")
    @PreAuthorize("permitAll()")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // Note: You must ensure your ProductService has a method called listByCategoryId!
        return productService.listByCategoryId(categoryId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // ONLY Admins can create
    @ResponseStatus(HttpStatus.CREATED) // Returns a 201 Created status when successful
    public Category addCategory(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // ONLY Admins can update
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // ONLY Admins can delete
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns a 204 No Content status when successful
    public void deleteCategory(@PathVariable int id) {
        categoryService.delete(id);
    }
}