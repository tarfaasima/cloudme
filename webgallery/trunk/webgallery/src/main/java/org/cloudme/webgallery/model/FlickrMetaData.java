package org.cloudme.webgallery.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings( "serial" )
@Unindexed
public class FlickrMetaData implements IdObject<Long> {
    @Id
    @Indexed
    private Long id;
    private String key;
    private String secret;
    private String nsid;
    private String perms;
    private String fullname;
    private String token;
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
