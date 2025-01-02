package de.thws.milu.core.domain.model;

import java.util.UUID;

public interface Todo {

    enum State {
        CREATED,
        IN_PROGRESS,
        CLOSED
    }

    UUID getId();

    String getName();

    String getDescription();

    State getState();

    Board getParentBoard();
}
