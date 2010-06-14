package org.cloudme.webgallery.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings( "serial" )
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FlickrMetaData implements IdObject<Long> {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String key;
    @Persistent
    private String secret;
    @Persistent
    private String nsid;
    @Persistent
    private String perms;
    @Persistent
    private String fullname;
    @Persistent
    private String token;
    @Persistent
    private String username;

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getNsid() {
        return nsid;
    }

    public String getPerms() {
        return perms;
    }

    public String getFullname() {
        return fullname;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setNsid(String nsid) {
        this.nsid = nsid;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
