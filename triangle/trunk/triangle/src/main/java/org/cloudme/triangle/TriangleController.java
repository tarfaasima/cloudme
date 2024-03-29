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

import org.cloudme.util.MultiKeyMap;

/**
 * The main controller of the framework. Here the {@link Entity}s are registered
 * initially.
 * 
 * @author Moritz Petersen
 */
public class TriangleController {
    /**
     * The {@link EntityResolver} of this {@link TriangleController}.
     */
    private EntityResolver entityResolver;
    /**
     * The main {@link Entity}, usually the starting point of an application.
     */
    private Entity<?> mainEntity;
    /**
     * All entities registered.
     */
    private final MultiKeyMap<Entity<?>> entities = new MultiKeyMap<Entity<?>>();

    /**
     * Adds {@link Entity} types to this {@link TriangleController}, which get
     * automatically parsed and configured.
     * 
     * @param types
     *            The classes of the {@link Entity}s.
     */
    @SuppressWarnings( "unchecked" )
    public void register(Class<?>... types) {
        for (final Class<?> entityClass : types) {
            final Entity<?> entity = new Entity(entityClass);
            entities.put(entity, entity.getName(), entity.getType());
            if (entityResolver != null) {
                entityResolver.addEntity(entity);
            }
            if (entity.isMainEntity()) {
                if (mainEntity != null) {
                    throw new IllegalStateException("Main entity already exists ("
                            + mainEntity.getName() + "): " + entity.getName());
                }
                mainEntity = entity;
            }
        }
    }

    /**
     * The main {@link Entity}, usually the starting point of an application.
     * 
     * @return The main {@link Entity}, usually the starting point of an
     *         application.
     */
    @SuppressWarnings( "unchecked" )
    public <T> Entity<T> getMainEntity() {
        return (Entity<T>) mainEntity;
    }

    /**
     * Returns the entity either by technical name or type.
     * 
     * @param key
     *            Either technical name or type of the entity.
     * @return The entity or null if not found.
     */
    @SuppressWarnings( "unchecked" )
    public <T> Entity<T> getEntity(Object key) {
        return (Entity<T>) entities.get(key);
    }

    /**
     * The {@link EntityResolver} of this {@link TriangleController}.
     * 
     * @param entityResolver
     *            The {@link EntityResolver} of this {@link TriangleController}.
     */
    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }
}
