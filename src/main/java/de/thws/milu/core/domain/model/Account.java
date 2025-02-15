package de.thws.milu.core.domain.model;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface Account extends Principal {

    UUID getId();

    @Override
    String getName();

    String getPassword();

    List<? extends Board> getBoards();
}
