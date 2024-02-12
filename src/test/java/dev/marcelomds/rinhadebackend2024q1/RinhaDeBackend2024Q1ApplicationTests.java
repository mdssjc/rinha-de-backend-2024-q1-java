package dev.marcelomds.rinhadebackend2024q1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class RinhaDeBackend2024Q1ApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> RinhaDeBackend2024Q1Application.main(new String[]{}));
    }
}
