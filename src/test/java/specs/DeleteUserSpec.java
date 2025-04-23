package specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static specs.BaseSpecs.baseSpec;

public class DeleteUserSpec {
    public static final RequestSpecification deleteUserRequestSpec = with()
            .spec(baseSpec)
            .pathParam("userId", 2);
}
