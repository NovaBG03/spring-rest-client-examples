package com.example.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Card implements Serializable {
    private String type;
    private String number;
    @JsonProperty("expiration_date")
    private ExpirationDate expirationDate;
    private String iban;
    private String swift;
}
