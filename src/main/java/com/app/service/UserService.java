package com.app.service;

import java.util.Map;

import com.app.dto.UserRequest;

public interface UserService {

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);
	
	public boolean isEmailExists(String email);

	public boolean saveUser(UserRequest userRequest);
}
