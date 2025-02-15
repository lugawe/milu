package de.thws.milu.adapter.in.json;

import de.thws.milu.core.domain.model.Account;

import java.security.Principal;
import java.util.UUID;

public class JsonAuthAccount implements Principal {

    private final UUID id;
    private final String name;

    public JsonAuthAccount(Account account) {
        this.id = account.getId();
        this.name = account.getName();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
