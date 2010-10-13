package org.cloudme.gaestripes;

import javax.persistence.Id;

public class DomainObject {

    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
