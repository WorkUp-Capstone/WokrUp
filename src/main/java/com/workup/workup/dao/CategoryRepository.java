package com.workup.workup.dao;

import com.workup.workup.models.Category;
import com.workup.workup.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query(value = "from Category c where c.name like %:keyword%")
  List<Category> getCategoriesByKeyword(@Param("keyword") String keyword);

  List<Project> findProjectById(Long id);

  Category findByName(String name);
}
