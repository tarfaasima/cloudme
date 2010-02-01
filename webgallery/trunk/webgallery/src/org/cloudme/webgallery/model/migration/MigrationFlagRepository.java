package org.cloudme.webgallery.model.migration;

import org.cloudme.webgallery.persistence.jdo.AbstractJdoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MigrationFlagRepository extends AbstractJdoRepository<Long, MigrationFlag> {
    public MigrationFlagRepository() {
        super(MigrationFlag.class);
    }
}
