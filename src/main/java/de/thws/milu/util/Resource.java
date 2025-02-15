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


    public void addLink(String rel, String href) {
        this.links.add(new Link(rel, href));
    }
}
