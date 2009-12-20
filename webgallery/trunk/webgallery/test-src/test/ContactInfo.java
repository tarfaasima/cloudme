package test;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.cloudme.webgallery.IdObject;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ContactInfo implements IdObject<String> {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    private String streetAddress;

    public void setId(String key) {
        id = key;
    }

    public String getId() {
        return id;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
    
    @Override
    public String toString() {
        return "(" + id + ", " + streetAddress + ")";
    }
}
