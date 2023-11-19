package com.sunbaseAssignment.SunbaseAssignment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("street")
    private String street;

    @JsonProperty("address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;


}
