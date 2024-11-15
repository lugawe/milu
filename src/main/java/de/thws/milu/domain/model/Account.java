package de.thws.milu.domain.model;

import java.util.List;
import java.util.UUID;

public interface Account {

    UUID getId();

    String getName();

    List<? extends Board> getBoards();
}
