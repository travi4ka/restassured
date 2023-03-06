package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    public static RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://reqres.in/")
            .setBasePath("api/")
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();
}
