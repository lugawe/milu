package de.thws.milu.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Resource<T> {

    @JsonProperty("data")
    private T data;

    @JsonProperty("links")
    private List<Link> links = new ArrayList<>();

    public Resource() {
        // Default constructor needed for Jackson
    }

    public Resource(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(String rel, String href) {
        this.links.add(new Link(rel, href));
    }
}