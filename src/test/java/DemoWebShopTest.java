import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemoWebShopTest {
    @Test
    public void addToCartApiTest() {
        step("Get cookie by api and add to cart", () -> {
            String authorizationCookie =
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
                            .extract().cookie("Nop.customer");


            step("Set cookie to browser", () -> {
                open("http://demowebshop.tricentis.com/");
                Cookie ck = new Cookie("Nop.customer",authorizationCookie);
                getWebDriver().manage().addCookie(ck);
                refresh();
            });

            step("Check cart", () -> {
                $(".cart-qty").shouldHave(text("2"));
            });
        });
    }
}
