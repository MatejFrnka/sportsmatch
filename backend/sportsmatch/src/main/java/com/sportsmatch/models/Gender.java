package com.sportsmatch.models;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");
    private final String displayValue;

    Gender(String displayValue) {
        this.displayValue = displayValue;
    }
}
