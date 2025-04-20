package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class DeleteUserSpec {
    public static RequestSpecification deleteUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .pathParam("userId", 2);

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .log(BODY)
            .build();
}
