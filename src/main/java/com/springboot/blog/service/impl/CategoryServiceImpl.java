package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDTO;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {

        Category category=modelMapper.map(categoryDTO, Category.class);
        Category savedCategory= categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id", String.valueOf(categoryId)));
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category,CategoryDTO.class)).collect(Collectors.toList());



    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id", String.valueOf(categoryId)));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category categoryToDelete = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(categoryId)));
        categoryRepository.deleteById(categoryId);
        return modelMapper.map(categoryToDelete,CategoryDTO.class);
    }


}
