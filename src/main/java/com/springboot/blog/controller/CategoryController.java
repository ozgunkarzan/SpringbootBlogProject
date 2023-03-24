package com.springboot.blog.controller;


import com.springboot.blog.payload.CategoryDTO;
import com.springboot.blog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //Building Add Category REST API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){

        CategoryDTO savedCategory= categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }


    //Building Get Category REST API
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable(name = "id") Long id){

        CategoryDTO categoryDTO= categoryService.getCategory(id);
        return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
    }


    //Building Get All Categories REST API
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categoryDTOS= categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDTOS,HttpStatus.OK);
    }

    //Building Update Category REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public  ResponseEntity<CategoryDTO> updateCategory(@PathVariable(name = "id") Long id, @RequestBody CategoryDTO categoryDTO){
        CategoryDTO updatedCategoryDTO=categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategoryDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
     public ResponseEntity deleteCategory(@PathVariable(name = "id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}
