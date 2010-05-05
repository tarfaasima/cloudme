package org.cloudme.triangle;

import org.cloudme.triangle.annotations.Label;
import org.cloudme.triangle.annotations.MainEntity;
import org.cloudme.triangle.annotations.Max;
import org.cloudme.triangle.annotations.Min;
import org.cloudme.triangle.annotations.Required;

@Label("Test")
@MainEntity
public class TestEntity {
    @Label("Name")
    @Required
    @Max(10)
    @Min(3)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}