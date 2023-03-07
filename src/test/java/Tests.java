import static io.qameta.allure.Allure.step;

import io.restassured.path.json.JsonPath;
import models.requests.PersonPutRequest;
import models.requests.UserRegistrationRequest;
import models.responses.ListUsersResponse;
import models.responses.PersonPutResponse;
import models.responses.SingleUserResponse;
import models.responses.UnsuccessfulRegistrationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.ResponseSpec;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RequestSpec.requestSpecification;
import static specs.ResponseSpec.responseSpecification;
import static org.hamcrest.CoreMatchers.*;


public class Tests {

    @Test
    @DisplayName("List users")
    public void listUsers() {
        step("Checking number of pages", () -> {
            ListUsersResponse listUsersResponse = given().spec(requestSpecification)
                    .when()
                    .get("users?page=2")
                    .then()
                    .spec(ResponseSpec.responseSpecification)
                    .statusCode(200)
                    .extract()
                    .as(ListUsersResponse.class);
            assertEquals(2, listUsersResponse.getPage());
        });
    }

    @Test
    @DisplayName("List users Groovy")
    public void listUsersGroovy() {
        step("Checking number of pages", () -> {
            given().spec(requestSpecification)
                    .when()
                    .get("users?page=2")
                    .then()
                    .spec(ResponseSpec.responseSpecification)
                    .statusCode(200)
                    .body("data.findAll{it}.id.flatten()", hasItem(10));
        });
    }

    @Test
    @DisplayName("List users Groovy 2")
    public void listUsersGroovy2() {
        step("Checking number of pages", () -> {
            given().spec(requestSpecification)
                    .when()
                    .get("users?page=2")
                    .then()
                    .spec(ResponseSpec.responseSpecification)
                    .statusCode(200)
                    .body("data.findAll{it.email.contains('@')}.flatten().size()", is(6));
        });
    }

    @Test
    @DisplayName("List users JsonPath")
    public void listUsersJsonPath() {
        step("Checking number of pages", () -> {
            JsonPath jsonPath = given().spec(requestSpecification)
                    .when()
                    .get("users?page=2")
                    .then()
                    .spec(ResponseSpec.responseSpecification)
                    .statusCode(200)
                    .extract().jsonPath();
            assertEquals(2, (Integer) jsonPath.get("page"));
        });
    }


    @Test
    @DisplayName("Single User")
    public void singleUserGet() {
        step("Checking user's email", () -> {
            SingleUserResponse singleUserResponse = given().spec(requestSpecification)
                    .when()
                    .get("users/2")
                    .then()
                    .spec(ResponseSpec.responseSpecification)
                    .statusCode(200)
                    .extract()
                    .as(SingleUserResponse.class);
            assertEquals("janet.weaver@reqres.in", singleUserResponse.getData().getEmail());
        });
    }

    @Test
    @DisplayName("Single User Groovy")
    public void singleUserGetGroovy() {
        step("Checking user's email", () -> {
            given().spec(requestSpecification)
                    .when()
                    .get("users/2")
                    .then()
                    .spec(ResponseSpec.responseSpecification)
                    .statusCode(200)
                    .body("data.email", is("janet.weaver@reqres.in"));
        });
    }

    @Test
    @DisplayName("Single User JsonPath")
    public void singleUserGetJsonPath() {
        step("Checking user's email", () -> {
            JsonPath jsonPath = given().spec(requestSpecification)
                    .when()
                    .get("users/2")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(200)
                    .extract().jsonPath();
            assertEquals("https://reqres.in/#support-heading", jsonPath.get("support.url"));
        });
    }

    @Test
    @DisplayName("Register - unsuccessful")
    public void apiLoginNegative() {
        step("Registration of user without password entered", () -> {
            UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
            userRegistrationRequest.setEmail("peter@klaven");

            UnsuccessfulRegistrationResponse unsuccessfulRegistrationResponse = given().spec(requestSpecification)
                    .body(userRegistrationRequest)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(400)
                    .extract()
                    .as(UnsuccessfulRegistrationResponse.class);
            assertEquals("Missing password", unsuccessfulRegistrationResponse.getError());
        });
    }

    @Test
    @DisplayName("Update")
    public void apiUserPut() {
        step("User update", () -> {
            PersonPutRequest personPutRequest = new PersonPutRequest();
            personPutRequest.setName("morpheus123");
            personPutRequest.setJob("123zion resident");

            PersonPutResponse personPutResponse = given().spec(requestSpecification)
                    .body(personPutRequest)
                    .when()
                    .put("/users/2")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(200)
                    .extract()
                    .as(PersonPutResponse.class);

            assertEquals("morpheus123", personPutResponse.getName());
            assertEquals("123zion resident", personPutResponse.getJob());
        });
    }

    @Test
    @DisplayName("Single <resource> not found")
    public void getUnknownResource() {
        step("Checking status of error when unknown page opens", () -> {
            given().spec(requestSpecification)
                    .when()
                    .get("/unknown/23")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(404);
        });
    }
}
