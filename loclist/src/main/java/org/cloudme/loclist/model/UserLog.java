package org.cloudme.loclist.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
@Cached
public class UserLog extends DomainObject {
    private String authDomain;
    private String email;
    private String federatedIdentity;
    private String nickname;
    @Indexed
    private String userId;
    private Date timestamp = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin")).getTime();

    public String getAuthDomain() {
        return authDomain;
    }

    public void setAuthDomain(String authDomain) {
        this.authDomain = authDomain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFederatedIdentity() {
        return federatedIdentity;
    }

    public void setFederatedIdentity(String federatedIdentity) {
        this.federatedIdentity = federatedIdentity;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
