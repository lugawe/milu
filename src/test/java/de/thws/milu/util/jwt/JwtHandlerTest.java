package de.thws.milu.util.jwt;

import static org.junit.jupiter.api.Assertions.*;

import java.security.Principal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JwtHandlerTest {

    static class User implements Principal {

        private String name;

        public User() {}

        public User(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static final String TEST_NAME = "Mustermann";

    static JwtHandler jwtHandler;

    @BeforeAll
    static void beforeAll() {
        jwtHandler = new JwtHandler("secret1234", 10);
    }

    String createTestToken() {
        User user = new User(TEST_NAME);
        return jwtHandler.encode(user);
    }

    @Test
    void test_encode() {
        String token = createTestToken();
        assertNotNull(token);
    }

    @Test
    void test_decode() {
        String token = createTestToken();
        Optional<User> user1 = jwtHandler.decode(token, User.class);

        assertTrue(user1.isPresent());
        assertEquals(TEST_NAME, user1.get().getName());

        Optional<User> user2 = jwtHandler.decode("bla", User.class);

        assertTrue(user2.isEmpty());
    }
}
