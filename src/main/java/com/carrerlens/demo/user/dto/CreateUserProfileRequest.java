package com.carrerlens.demo.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserProfileRequest {
    private String name;
    private String desiredJobCategory;
    private String preferredCountryCode;
    private String languageLevel;
}
