package de.thws.milu.domain.model;

import java.util.UUID;

public interface Todo {

    UUID getId();

    String getName();

    String getDescription();

    Board getParentBoard();
}
