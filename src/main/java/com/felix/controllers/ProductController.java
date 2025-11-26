package com.felix.controllers;

import com.felix.dto.CategoryDTO;
import com.felix.dto.ProductDTO;
import com.felix.services.CategoryService;
import com.felix.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            Pageable pageable
            //@RequestParam(value = "page",defaultValue = "0") Integer page,
            //@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            //@RequestParam(value = "orderBy",defaultValue = "name") String orderBy,
            //@RequestParam(value = "direction",defaultValue = "ASC") String direction
    ){

       // PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Page<ProductDTO> list = productService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<ProductDTO>> findById(@PathVariable Long id){
        ProductDTO dto = productService.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(dto));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){
        dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,@RequestBody ProductDTO dto){
        dto = productService.update(id,dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
