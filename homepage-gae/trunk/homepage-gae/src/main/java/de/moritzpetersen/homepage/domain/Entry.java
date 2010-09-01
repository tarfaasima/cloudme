package de.moritzpetersen.homepage.domain;

import java.util.Date;

import javax.persistence.Id;

public class Entry {
    @Id
    private Long id;
    private String content;
    private Date date;
    private String oldId;
    private String origin;
    private String title;
    private String url;

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public String getOldId() {
        return oldId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }
}
