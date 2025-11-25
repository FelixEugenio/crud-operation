package com.felix.dto;

import com.felix.models.Category;

import java.io.Serializable;
import java.util.Objects;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // >>> FALTAVA ISSO <<<
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // <<< FIM DO QUE FALTAVA >>>

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}