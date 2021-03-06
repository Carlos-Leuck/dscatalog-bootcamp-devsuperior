package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // only to show how to do a transaction. readOnly = true; Use to avoid "lock" the db. Increases the perfomance.
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        // remember: repository works with entity Category.
        Page<Category> list = repository.findAll(pageable);
        //so we need to use this lambda to convert Category to CategoryDTO.
        // Page is already a Stream so we dont need to use .stream() and .collect(Collectors.toList())
        return list.map(CategoryDTO::new);

    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        // try to find the obj with id. Remember: Optional could be null
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        // converts Category to CategoryDTO (using constructor)
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        // convert dto to entity (Category). Remember: ID is auto incremented.
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        // convert entity to dto
        return new CategoryDTO(entity);

    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);


        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found: " + id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");


        }
    }
}
