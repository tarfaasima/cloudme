package org.cloudme.triangle;

import javax.persistence.Id;

import org.cloudme.triangle.annotation.ParentEntity;

public class MyChildEntity {
    @Id
    private Long id;

    private String name;

    @ParentEntity( type = MyEntity.class )
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
