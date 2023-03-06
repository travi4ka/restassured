import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class Tests {
    final String API_URL = "https://reqres.in";

    @Test
    @DisplayName("List users")
    public void listUsers() {
        given().when()
                .get(API_URL + "/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", is(2));
    }

    @Test
    @DisplayName("Single User")
    public void singleUserGet() {
        given().when()
                .get(API_URL + "/api/users/2")
                .then()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    @DisplayName("Register - unsuccessful")
    public void apiLoginNegative() {
        given().body("{\n" + "    \"email\": \"peter@klaven\"\n" + "}")
                .when()
                .post(API_URL + "/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    @DisplayName("Update")
    public void apiUserPut() {
        given().contentType(ContentType.JSON)
                .body("{\n" + "  \"name\": \"morpheus123\",\n" + "  \"job\": \"123zion resident\"\n" + "}")
                .when()
                .put(API_URL + "/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus123"))
                .body("job", is("123zion resident"));
    }

    @Test
    @DisplayName("Single <resource> not found")
    public void getUnknownResource() {
        given().when()
                .get(API_URL + "/api/unknown/23")
                .then()
                .statusCode(404);
    }
}
