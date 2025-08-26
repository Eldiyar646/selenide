package com.selenide.layers.web.page.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ProductsList {

    private String name;
    private int quantity;
    private String price;
    private String total;
}


