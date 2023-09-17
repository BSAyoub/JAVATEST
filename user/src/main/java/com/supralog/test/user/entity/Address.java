package com.supralog.test.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country ;

    public Address() {
        this.country = "Unknown";
    }
}
