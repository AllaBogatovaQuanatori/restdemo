package junit;

import org.junit.jupiter.api.*;

public class JUnitTest {

    @BeforeAll
    static void setup(){
        System.out.println("@Before all tests");
    }

    @BeforeEach
    void init(){
        System.out.println("@BeforeEach");
    }

    @Test
    void test(){
        System.out.println("Main test");
    }

    @AfterEach
    void after(){
        System.out.println("@AfterEach - after each test");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("@AfterAll - after all tests");
    }
}
