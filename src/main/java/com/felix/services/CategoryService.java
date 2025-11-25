package com.felix.services;

import com.felix.dto.CategoryDTO;
import com.felix.exceptions.DatabaseException;
import com.felix.exceptions.EntityNotFoundException;
import com.felix.models.Category;
import com.felix.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.hibernate.query.sqm.mutation.internal.temptable.LocalTemporaryTableInsertStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();
       return list.stream().map(x-> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(()->new EntityNotFoundException("Entity not found!"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
       Category entity = new Category();
       entity.setName(dto.getName());
       entity = categoryRepository.save(entity);
       return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id ,CategoryDTO dto) {
        try{
            Category entity = categoryRepository.getOne(id);
            entity.setName(dto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        }catch (EntityNotFoundException e){
           throw new EntityNotFoundException("Entity not found!");
        }
    }

    @Transactional
    public void delete(Long id) {
        try{
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw  new EntityNotFoundException("Entity not found!");
        }catch (DataIntegrityViolationException e){
         throw  new DatabaseException("Integrity violation!");
        }
    }
}
