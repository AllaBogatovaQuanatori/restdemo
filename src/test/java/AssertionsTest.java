import com.github.javafaker.Faker;
import org.example.dto.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AssertionsTest {
    public Faker faker;

    @Test
    public void assertTest() {
        faker = new Faker();
        User frodo = User.builder().username("test").password("1234567").email("test@mordor.com").build();
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

        assertThat(frodo.getPassword()).withFailMessage("should be %s", frodo)
                .isEqualTo(sam.getPassword());

        /*assertThat(frodo.getUsername()).overridingErrorMessage(() -> "Expecting Frodo to be a tester but was not.").
                isEqualTo("test");*/
    }

    @Test
    public void descriptionTest() {
        faker = new Faker();
        User frodo = createUser();

        assertThat(frodo.getEmail()).as("check %s's email", frodo.getEmail())
                .isEqualTo("Tests@mordor.com");
    }

    @Test
    public void recursiveComparisonTest() {
        faker = new Faker();
        User frodo = User.builder().username("test").password("test").email("test").build();
        User sam = User.builder().username("test").password("test").email("test").build();

        assertThat(frodo).usingRecursiveComparison().isEqualTo(sam); //PASS

        assertThat(frodo).isEqualTo(sam); //FAIL
    }

    @Test
    public void ignoreSomeFieldsTest() {
        faker = new Faker();
        User frodo = User.builder().username("test").password("1234567").email("test").build();
        User sam = User.builder().username("test").password("000000").email("test").build();

        assertThat(frodo).usingRecursiveComparison().ignoringFields("email").isEqualTo(sam); //PASS

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
