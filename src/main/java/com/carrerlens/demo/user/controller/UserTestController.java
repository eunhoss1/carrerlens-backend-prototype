package com.carrerlens.demo.user.controller;

import com.carrerlens.demo.user.dto.CreateUserProfileRequest;
import com.carrerlens.demo.user.dto.UserSkillRequest;
import com.carrerlens.demo.user.entity.UserProfile;
import com.carrerlens.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/users")
@RequiredArgsConstructor
public class UserTestController {

    private final UserService userService;

    @PostMapping
    public UserProfile createUser(@RequestBody CreateUserProfileRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/{userId}/skills")
    public String saveUserSkills(@PathVariable Long userId,
                                 @RequestBody UserSkillRequest request) {
        return userService.saveUserSkills(userId, request);
    }
}