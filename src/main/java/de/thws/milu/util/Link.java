package de.thws.milu.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {

    @JsonProperty("rel")
    private String rel;

    @JsonProperty("href")
    private String href;

    public Link() {
        // Default constructor needed for Jackson
    }

    public Link(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}