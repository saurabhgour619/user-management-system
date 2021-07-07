package com.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserRequest;
import com.app.service.UserService;

@RestController
public class UserRegController {

	@Autowired
	private UserService userService;

	@GetMapping("/countries")
	public Map<Integer, String> getCountries() {
		return userService.getCountries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		return userService.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {
		return userService.getCities(stateId);
	}

	@GetMapping("/emailCheck/{email}")
	public boolean uniqueEmailCheck(@PathVariable String email) {
		return userService.isEmailExists(email);
	}

	@PostMapping("/saveUser")
	public boolean saveUser(@RequestBody UserRequest userRequest) {
		return userService.saveUser(userRequest);
	}
}
