package com.felix.services;

import com.felix.dto.CategoryDTO;
import com.felix.dto.ProductDTO;
import com.felix.exceptions.DatabaseException;
import com.felix.exceptions.EntityNotFoundException;
import com.felix.models.Category;
import com.felix.models.Product;
import com.felix.repositories.CategoryRepository;
import com.felix.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = productRepository.findAll(pageRequest);
       return list.map(x-> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product product = obj.orElseThrow(()->new EntityNotFoundException("Entity not found!"));
        return new ProductDTO(product,product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
       Product entity = new Product();
       copyDTOtoEntity(dto,entity);
       entity = productRepository.save(entity);
       return new ProductDTO(entity,entity.getCategories());
    }

    public void copyDTOtoEntity(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setDate(dto.getDate());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for(CategoryDTO catDto: dto.getCategories()){
            Category category = categoryRepository.getOne(catDto.getId());
            entity.getCategories().add(category);
        }
    }

    @Transactional
    public ProductDTO update(Long id ,ProductDTO dto) {
        try{
            Product entity = productRepository.getOne(id);
            copyDTOtoEntity(dto,entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity,entity.getCategories());
        }catch (EntityNotFoundException e){
           throw new EntityNotFoundException("Entity not found!");
        }
    }

    @Transactional
    public void delete(Long id) {
        try{
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw  new EntityNotFoundException("Entity not found!");
        }catch (DataIntegrityViolationException e){
         throw  new DatabaseException("Integrity violation!");
        }
    }
}
