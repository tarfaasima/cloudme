package org.cloudme.triangle;

import org.cloudme.triangle.annotation.Label;
import org.cloudme.triangle.annotation.MainEntity;
import org.cloudme.triangle.annotation.Mask;
import org.cloudme.triangle.annotation.Max;
import org.cloudme.triangle.annotation.Min;
import org.cloudme.triangle.annotation.Required;

@Label( "Test" )
@MainEntity
public class TestEntity {
    @Label( "Name" )
    @Required
    @Max( 10 )
    @Min( 3 )
    private String name;

    @Mask( "[^x]*" )
    private String noX;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNoX(String noX) {
        this.noX = noX;
    }

    public String getNoX() {
        return noX;
    }
}