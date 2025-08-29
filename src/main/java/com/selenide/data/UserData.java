package com.selenide.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.datafaker.Faker;

@Getter
@Setter
@ToString

public class UserData {

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
