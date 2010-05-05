package org.cloudme.triangle;

public class TriangleController {
    private EntityResolver entityResolver;
    private Entity mainEntity;

    public void addEntity(Class<?>... types) {
        for (final Class<?> entityClass : types) {
            final Entity entity = new Entity(entityClass);
            entityResolver.addEntity(entity);
            if (entity.isMainEntity()) {
                if (mainEntity != null) {
                    throw new IllegalStateException("Main entity already exists (" + mainEntity.getName() + "): "
                            + entity.getName());
                }
                mainEntity = entity;
            }
        }
    }

    public Entity getMainEntity() {
        return mainEntity;
    }

    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }
}
