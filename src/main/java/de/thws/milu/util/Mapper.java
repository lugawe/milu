package de.thws.milu.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public final class Mapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Mapper() {}

    public static Map<String, ?> toMap(Object o) {

        TypeReference<HashMap<String, ?>> typeRef = new TypeReference<>() {};

        return objectMapper.convertValue(o, typeRef);
    }
}
