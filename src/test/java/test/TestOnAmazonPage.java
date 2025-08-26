package test;

import base.BaseTest;
import com.selenide.layers.web.page.amazon.AmazonBrands;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestOnAmazonPage extends BaseTest {

    @Test
    @Order(1)
    void getBands() {
        var brands = open(AmazonBrands.class)
                .waitForPageLoaded()
                .search("Iphone")
                .getAllBrands();
        System.out.println("############################");
        brands.forEach(System.out::println);

    }

    @Test
    @Order(2)
    void getConditions() {
        var conditions = open(AmazonBrands.class)
                .waitForPageLoaded()
                .search("Iphone")
                .getAllConditions();
        System.out.println("############################");
        conditions.forEach(System.out::println);

    }
}
