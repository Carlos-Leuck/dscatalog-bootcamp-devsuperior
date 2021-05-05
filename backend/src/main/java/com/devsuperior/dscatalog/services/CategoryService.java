package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // only to show how to do a transaction. readOnly = true; Use to avoid "lock" the db. Increases the perfomance.
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll(); // remember! repository works with entity Category.
        //so we need to use this lambda to convert Category to CategoryDTO.
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

    }
}
