package de.thws.milu.util;

import java.util.ArrayList;
import java.util.List;

public class Resource<T> {
    private T data;
    private List<Link> links = new ArrayList<>();

    public Resource() {}

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
