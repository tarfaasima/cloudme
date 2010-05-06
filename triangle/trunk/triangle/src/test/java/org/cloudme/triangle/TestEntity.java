package org.cloudme.triangle;

import org.cloudme.triangle.annotation.Label;
import org.cloudme.triangle.annotation.MainEntity;
import org.cloudme.triangle.annotation.Mask;
import org.cloudme.triangle.annotation.Max;
import org.cloudme.triangle.annotation.Min;
import org.cloudme.triangle.annotation.Required;
import org.cloudme.triangle.annotation.ValidatorType;
import org.cloudme.triangle.validation.EmailValidator;

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

    @Min( 18 )
    private int age;

    @Required
    private boolean active;

    @ValidatorType( EmailValidator.class )
    private String email;

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

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}