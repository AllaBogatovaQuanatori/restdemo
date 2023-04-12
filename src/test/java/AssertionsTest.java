import com.github.javafaker.Faker;
import org.example.dto.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;


public class AssertionsTest {
    public Faker faker;


    @Test
    public void baseAssertTest() {
/*        int actualValue = 5;
        int expectedValue = 5;
        assertThat(actualValue).isEqualTo(expectedValue);

        int act = 7;
        int expe = 5;
        assertThat(act).isEqualTo(expe);*/

        List<String> list = Arrays.asList("foo", "bar", "baz");
        assertThat(list).contains("foo");

        List<String> list0 = Arrays.asList("foo", "bar", "baz");
        assertThat(list0).contains("ftttto");

        List<String> list2 = Arrays.asList("foo", "bar", "baz");
        assertThat(list2).containsOnly("foo", "bar", "baz");

        List<String> list3 = Arrays.asList("foo", "bar", "baz", "gghggg");
        assertThat(list3).containsOnly("foo", "bar", "baz");

        List<String> emptyList = new ArrayList<>();
        assertThat(emptyList).isNotEmpty();

        String actualString = "Hello, world!";
        assertThat(actualString).contains("world");

        int actualValue1 = 5;
        int expectedValue1 = 10;
        assertThat(actualValue1).isLessThan(expectedValue1);

        int actualValue2 = 10;
        int expectedValue2 = 5;
        assertThat(actualValue2).isGreaterThan(expectedValue2);


        List<String> list7 = Arrays.asList("foo", "bar", "baz");
        assertThat(list7).element(1).isEqualTo("bar");

        int actualValueP = 5;
        Predicate<Integer> predicate = value -> value > 0 && value < 10;
        assertThat(actualValueP).matches(predicate);
    }

    @Test
    public void assertTest() {
        faker = new Faker();
        User frodo = User.builder().username("test").password("1234567").email("test@mordor.ru").build();
        assertThat(frodo.getEmail()).isNotNull().
                startsWith("test").
                contains("@").
                endsWith("com");
    }

    @Test
    public void overridingErrorMessageTest() {
        faker = new Faker();

        User frodo = createUser();
        User sam = createUser();

/*        assertThat(frodo.getPassword())
                .isEqualTo(sam.getPassword());*/

//        assertThat(frodo.getPassword()).withFailMessage("should be %s", frodo)
//                .isEqualTo(sam.getPassword());

        assertThat(frodo.getUsername()).overridingErrorMessage(() -> "Expecting Frodo to be a tester but was not.").
                isEqualTo("test");
    }

    @Test
    public void descriptionTest() {
        faker = new Faker();
        User frodo = createUser();

        assertThat(frodo.getEmail()).as("check %s's email", frodo.getEmail())
                .isEqualTo("Tests@mordor.com");
        assertThat(frodo.getName()).as("check %s's name", frodo.getName())
                .isEqualTo("000");
    }

    @Test
    public void recursiveComparisonTest() {
        faker = new Faker();
        User frodo = User.builder().username("test").password("test").email("test").build();
        User sam = User.builder().username("test").password("test").email("test").build();

        assertThat(frodo).usingRecursiveComparison().isEqualTo(sam); //PASS

    //    assertThat(frodo).isEqualTo(sam); //FAIL
    }

    @Test
    public void ignoreSomeFieldsTest() {
        faker = new Faker();
        User frodo = User.builder().username("test").password("1234567").email("test").build();
        User sam = User.builder().username("test").password("000000").email("test").build();

        assertThat(frodo).usingRecursiveComparison().ignoringFields("password").isEqualTo(sam); //PASS

        frodo.setPassword(null);
        sam.setPassword(null);

        assertThat(frodo).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(sam); //PASS

        assertThat(frodo).isEqualTo(sam); //FAIL
    }

    @Test
    public void ignoreExpectedFieldsTest() {
        faker = new Faker();
        User frodo = User.builder().username("test").password("1234567").email("test").build();
        User sam = User.builder().username("test").password(null).email("test").build();

        assertThat(frodo).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(sam); //PASS

        assertThat(sam).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(frodo); //FAIL

    }

    public User createUser() {
        return User.builder().
                username(faker.cat().name()).
                email(faker.internet().emailAddress()).
                password(faker.dragonBall().character()).build();
    }
}
