package com.ngoctta.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngoctta.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
