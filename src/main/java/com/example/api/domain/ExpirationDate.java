package com.example.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ExpirationDate implements Serializable {
    private String date;
    @JsonProperty("timezone_type")
    private Integer timezoneType;
    private String timezone;
}
