package de.thws.milu.util.jpa;

import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.List;

public final class JpaHelper {

    private JpaHelper() {}

    public static Object getSingleResultOrNull(Query query) {
        List<?> results = query.getResultList();
        if (results.isEmpty()) return null;
        else if (results.size() == 1) return results.getFirst();
        throw new NonUniqueResultException();
    }
}
