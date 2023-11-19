package com.sunbaseAssignment.SunbaseAssignment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenModel {

    @JsonProperty("access_token")
    private String access_token;
}
