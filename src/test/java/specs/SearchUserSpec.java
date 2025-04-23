package specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static specs.BaseSpecs.baseSpec;

public class SearchUserSpec {
    public static final RequestSpecification searchUserRequestSpec = with()
            .spec(baseSpec)
            .pathParam("userId", 2);

}
