package de.thws.milu.util;

import java.util.List;

public final class Casting {

    private Casting() {}

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> cast(List<?> list) {
        return (List<T>) list;
    }
}
