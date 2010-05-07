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

/**
 * An {@link EntityResolver} provides methods to the {@link TriangleController}
 * to resolve entities from the persistence facility. It is replaceable to allow
 * different implementations and persistence technologies, such as Google App
 * Engine datastore, Hibernate, File, XML etc.
 * 
 * @author Moritz Petersen
 */
public interface EntityResolver {
    /**
     * Adds the given {@link Entity} declaration to this resolver.
     * 
     * @param entity
     *            The {@link Entity} declaration.
     */
    void addEntity(Entity entity);
}
