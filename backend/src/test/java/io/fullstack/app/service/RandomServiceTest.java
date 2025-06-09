package io.fullstack.app.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomServiceTest {
    @Test
    @DisplayName("should be equal to 4, 5 and 6")
    void test() {
        assertEquals(4, 2 + 2);
        assertEquals(5, 2 + 3);
        assertEquals(6, 3 + 3);
    }
}
