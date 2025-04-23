package specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static specs.BaseSpecs.baseSpec;

public class UpdateUserSpec {
    public static final RequestSpecification updateUserRequestSpec = with()
            .spec(baseSpec)
            .pathParam("userId", 2);
}
