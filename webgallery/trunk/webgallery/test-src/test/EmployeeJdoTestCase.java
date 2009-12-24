package test;

import org.cloudme.webgallery.persistence.jdo.AbstractJdoRepository;
import org.cloudme.webgallery.persistence.jdo.AbstractJdoTestCase;


public class EmployeeJdoTestCase extends AbstractJdoTestCase<String, Employee> {
    @Override
    public Employee createEntity() {
        return new Employee();
    }

    @Override
    public AbstractJdoRepository<String, Employee> createRepository() {
        return new JdoEmployeeRepository();
    }
}
