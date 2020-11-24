package tests;

import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Owner("zhuravel")
public class RegressInTests {

    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @DisplayName("01. Просмотр данных ресурса")
    void shouldShowSingleResourse() {
        given()
                .when()
                .get("/unknown/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.name", is("fuchsia rose"));
    }

    @Test
    @DisplayName("02. Создание нового пользователя")
    void shouldCreateNewUser() {
        String requestData = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(requestData)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("id", is(notNullValue()));
    }

    @Test
    @DisplayName("03. Изменение всех данных пользователя")
    void shouldChangeUsersData() {
        String putRequestData = "src/test/resources/put_request_data.json";

        given()
                .body(putRequestData)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("updatedAt", is(notNullValue()));
    }

    @Test
    @DisplayName("04. Изменение строки 'job' у пользователя")
    void shouldChangeUsersJob() {
        String patchRequestData = "src/test/resources/patch_request_data.json";

        given()
                .body(patchRequestData)
                .when()
                .patch("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("updatedAt", is(notNullValue()));
    }

    @Test
    @DisplayName("05. Удаление пользователя")
    void shouldDeleteSingleUser() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204)
                .log().body()
                .body(is(isEmptyOrNullString()));
    }
}


