package test;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.cloudme.webgallery.model.IdObject;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Employee implements IdObject<String> {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    private List<ContactInfo> contactInfos;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContactInfos(List<ContactInfo> contactInfos) {
        this.contactInfos = contactInfos;
    }

    public List<ContactInfo> getContactInfos() {
        return contactInfos;
    }
    
    public void addContactInfo(ContactInfo contactInfo) {
        if (contactInfos == null) {
            contactInfos = new ArrayList<ContactInfo>();
        }
        contactInfos.add(contactInfo);
    }
    
    @Override
    public String toString() {
        return "(" + id + ", " + contactInfos + ")";
    }
}
