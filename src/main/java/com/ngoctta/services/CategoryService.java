package com.ngoctta.services;

import java.util.Optional;

import com.ngoctta.entity.Category;

public interface CategoryService {
	Iterable<Category> findAll();

	Optional<Category> findById(Long id);

	Category saveCategory(Category category);

	void deleteCategory(Long id);
}
