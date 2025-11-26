package com.felix.controllers;

import com.felix.dto.CategoryDTO;
import com.felix.models.Category;
import com.felix.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.DocFlavor;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(
            Pageable pageable
            //@RequestParam(value = "page",defaultValue = "0") Integer page,
            //@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            //@RequestParam(value = "orderBy",defaultValue = "name") String orderBy,
            //@RequestParam(value = "direction",defaultValue = "ASC") String direction
    ){

       // PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Page<CategoryDTO> list = categoryService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<CategoryDTO>> findById(@PathVariable Long id){
        CategoryDTO dto = categoryService.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(dto));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
        dto = categoryService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,@RequestBody CategoryDTO dto){
        dto = categoryService.update(id,dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
