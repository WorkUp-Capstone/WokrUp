package com.workup.workup.services;

import com.workup.workup.dao.CategoryRepository;
import com.workup.workup.models.Category;
import com.workup.workup.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryDao;

    public List<Category> getCategoriesByKeyword(String keyword) {
        return categoryDao.getCategoriesByKeyword(keyword);
    }

    public Category findByName(String keyword) {
        return categoryDao.findByName(keyword);
    }
}
