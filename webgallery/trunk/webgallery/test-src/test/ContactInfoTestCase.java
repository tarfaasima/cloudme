package test;

import org.cloudme.webgallery.persistence.jdo.AbstractJdoRepository;
import org.cloudme.webgallery.persistence.jdo.AbstractJdoTestCase;

public class ContactInfoTestCase extends AbstractJdoTestCase<String, ContactInfo> {
    @Override
    public ContactInfo createEntity() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setStreetAddress("Strasse 1");
        return contactInfo;
    }

    @Override
    public AbstractJdoRepository<String, ContactInfo> createRepository() {
        return new AbstractJdoRepository<String, ContactInfo>(ContactInfo.class) {
        };
    }
}
