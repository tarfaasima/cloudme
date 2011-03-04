package org.cloudme.gaestripes;

import javax.persistence.Id;

/**
 * Base class for all entities.
 * 
 * @author Moritz Petersen
 */
public class Entity {
    @Id
    private Long id;

    /**
     * Initializes an empty {@link Entity}
     */
    public Entity() {
        // do nothing
    }

    /**
     * Convenient constructor to create a {@link Entity} with the given
     * id.
     * 
     * @param id
     *            The id of the {@link Entity}
     */
    public Entity(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
