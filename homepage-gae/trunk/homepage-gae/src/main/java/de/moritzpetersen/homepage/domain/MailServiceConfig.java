package de.moritzpetersen.homepage.domain;

public class MailServiceConfig {
    public String token;
    public int serviceId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
