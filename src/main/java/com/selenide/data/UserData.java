package com.selenide.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.datafaker.Faker;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class UserData {

    public static final Faker faker = new Faker();

    private String name;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address1;
    private String address2;
    private String country;
    private String cityState;
    private String city;
    private String state;
    private String zipCode;
    private String phone;

}
