package test;

import org.cloudme.webgallery.persistence.jdo.AbstractJdoRepository;

public class JdoEmployeeRepository extends AbstractJdoRepository<String, Employee>{
    public JdoEmployeeRepository() {
        super(Employee.class);
    }
}
