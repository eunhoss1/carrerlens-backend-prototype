package com.carrerlens.demo.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserSkillRequest {
    private List<String> skillCodes;
}