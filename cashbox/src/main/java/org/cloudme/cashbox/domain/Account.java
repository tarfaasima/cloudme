package org.cloudme.cashbox.domain;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
public class Account extends DomainObject {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
