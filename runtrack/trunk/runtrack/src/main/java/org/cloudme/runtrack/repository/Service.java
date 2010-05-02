package org.cloudme.runtrack.repository;

public class Service<T> {
    private final Repository<T> repository;

    public Service(Repository<T> repository) {
        this.repository = repository;
    }

    public void put(T t) {
        repository.put(t);
    }

    public T find(long id) {
        return repository.find(id);
    }

    public void delete(long id) {
        repository.delete(id);
    }
}
