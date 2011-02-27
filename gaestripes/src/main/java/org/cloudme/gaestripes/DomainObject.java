package org.cloudme.gaestripes;

import javax.persistence.Id;

/**
 * Base class for all entities.
 * 
 * @author Moritz Petersen
 */
public class DomainObject {
    @Id
    private Long id;

    /**
     * Initializes an empty {@link DomainObject}
     */
    public DomainObject() {
        // do nothing
    }

    /**
     * Convenient constructor to create a {@link DomainObject} with the given
     * id.
     * 
     * @param id
     *            The id of the {@link DomainObject}
     */
    public DomainObject(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
