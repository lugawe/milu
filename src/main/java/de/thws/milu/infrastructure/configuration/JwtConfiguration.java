package de.thws.milu.infrastructure.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class JwtConfiguration {

    @Valid
    @NotNull
    private String secret;

    public JwtConfiguration() {}

    @JsonProperty("secret")
    public String getSecret() {
        return secret;
    }

    @JsonProperty("secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
