package de.thws.milu.infrastructure.persistence;

import de.thws.milu.adapter.out.persistence.jpa.JpaAccountRepository;
import de.thws.milu.application.port.out.AccountRepository;
import de.thws.milu.application.service.AccountService;

public class JpaConfiguration {

    private JpaAccountRepository jpaAccountRepository;

    public JpaConfiguration(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    public AccountService createAccountService() {
        AccountRepository accountRepository = jpaAccountRepository;
        return new AccountService(accountRepository);
    }
}
