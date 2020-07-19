package com.example.springrestclientexamples.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ExpirationDate implements Serializable {
    private String date;
    private String timezoneType;
    private String timezone;
}
