package junit;

import org.example.dto.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class JUnitAnnotationTest {

    @Test
    void testDisabled(){
      //  Assertions.assertEquals("Str", "Str");

        Assertions.assertTrue(4 < 6);
    }

    @Test
    @DisplayName("Test long name")
    void testDisplayName(){
        Assertions.assertEquals(1, 1);
    }

    @EnumSource(Locale.class)
    @ParameterizedTest
    void testLocaleEnum(Locale locale) {
        System.out.println(locale.name());
    }

    static Stream<Arguments> localeArgText() {
        return Stream.of(
                Arguments.of(Locale.EN, "Test1"),
                Arguments.of(Locale.RU, "Test2"),
                Arguments.of(Locale.RU, "Test3")
        );
    }

    @MethodSource
    @ParameterizedTest
    void localeArgText(Locale locale, String text) {
        System.out.println("Locale:" + locale);
        System.out.println("Text:" + text);
    }
}
