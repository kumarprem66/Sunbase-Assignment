package com.sunbaseAssignment.SunbaseAssignment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @JsonProperty("login_id")
    private String loginId;

    @JsonProperty("password")
    private String password;
}
