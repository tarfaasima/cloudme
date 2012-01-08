package org.cloudme.sugar;

public class Id<T, V> {
    private final V value;

    public Id(T object, V value) {
        this.value = value;
    }

    public static <T extends Entity, V> Id<T, Long> of(T entity) {
        return new Id<T, Long>(entity, entity.getId());
    }

    public static <T, V> Id<T, V> of(Class<T> type, V value) {
        return new Id<T, V>(null, value);
    }

    public V value() {
        return value;
    }
}
