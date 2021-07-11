package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserLoginRequest;
import com.app.service.UserService;

@RestController
public class UserLoginController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public String loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		return userService.checkLogin(userLoginRequest);
	}
}
