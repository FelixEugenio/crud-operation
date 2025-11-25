package com.felix.services;

import com.felix.dto.CategoryDTO;
import com.felix.models.Category;
import com.felix.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.hibernate.query.sqm.mutation.internal.temptable.LocalTemporaryTableInsertStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}
