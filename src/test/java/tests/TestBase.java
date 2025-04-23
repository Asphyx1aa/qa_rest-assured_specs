package tests;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class TestBase {
    @BeforeAll
    static void testSetup() {
        baseURI = "https://reqres.in/api";
    }
}
