// Copyright 2010 Moritz Petersen
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.cloudme.triangle;

import org.cloudme.triangle.annotation.Label;
import org.cloudme.triangle.annotation.MainEntity;
import org.cloudme.triangle.annotation.Mask;
import org.cloudme.triangle.annotation.Max;
import org.cloudme.triangle.annotation.Min;
import org.cloudme.triangle.annotation.Pattern;
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
    
    @Pattern( "0.0" )
    private float weight;

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

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}