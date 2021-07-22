import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemowebShopTest {

    @Test
    public void addToCartApiTest() {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("product_attribute_72_5_18=53&product_attribute_72_6_19=54&" +
                                "product_attribute_72_3_20=58&product_attribute_72_8_30=93&" +
                                "addtocart_72.EnteredQuantity=2")
                        .when()
                        .post("http://demowebshop.tricentis.com/addproducttocart/details/72/1")
                        .then()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("updatetopcartsectionhtml", is("(2)"))
                        .extract().response();

        System.out.println("" + response.path("updatetopcartsectionhtml"));
    }
}
