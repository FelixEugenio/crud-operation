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
       Product product = new Product();
       product.setName(dto.getName());
       product.setDescription(dto.getDescription());
       product.setImageUrl(dto.getImageUrl());
       product.setDate(dto.getDate());
       product.setPrice(dto.getPrice());
       product = productRepository.save(product);

       return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id ,ProductDTO dto) {
        try{
            Product product = productRepository.getOne(id);
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setImageUrl(dto.getImageUrl());
            product.setDate(dto.getDate());
            product.setPrice(dto.getPrice());
            product = productRepository.save(product);

            return new ProductDTO(product);
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
