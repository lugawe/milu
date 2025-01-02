package de.thws.milu.core.domain.model;

import java.util.List;
import java.util.UUID;

public interface Board {

    UUID getId();

    String getName();

    String getDescription();

    List<? extends Todo> getTodos();
}
