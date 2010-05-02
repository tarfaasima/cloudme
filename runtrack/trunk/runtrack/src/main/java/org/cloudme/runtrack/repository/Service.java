package org.cloudme.runtrack.repository;

public class Service<T> {
    private final Repository<T> repository;

    public Service(Repository<T> repository) {
        this.repository = repository;
    }

}
