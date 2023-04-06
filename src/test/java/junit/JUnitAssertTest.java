package junit;

import org.example.dto.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class JUnitAssertTest {

    @Test
    void test() {
        Assertions.assertEquals("Str", "Str");

        Assertions.assertTrue("Str".contains("S"));
        Assertions.assertTrue("Str".contains("Sq"));
    }

    @Test
    void testTF() {
        Assertions.assertTrue(5 > 2);
        Assertions.assertFalse(10 < 2);
    }

    @Test
    void testAssertAll() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(5 > 2),
                () -> Assertions.assertTrue(2 == 2),
                () -> Assertions.assertFalse(3 < 8),
                () -> Assertions.assertEquals("Test", "Test")
        );
    }


}
