package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.constants.AppConstants;
import com.app.dto.UserLoginRequest;
import com.app.dto.UserRegRequest;
import com.app.entity.CityEntity;
import com.app.entity.CountryEntity;
import com.app.entity.StateEntity;
import com.app.entity.UserEntity;
import com.app.repository.CityRepo;
import com.app.repository.CountryRepo;
import com.app.repository.StateRepo;
import com.app.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private UserRepo userRepo;

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryEntity> countries = countryRepo.findAll();
		return countries.stream().collect(Collectors.toMap(CountryEntity::getCountryId, CountryEntity::getCountryName));
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateEntity> states = stateRepo.findByCountryId(countryId);
		return states.stream().collect(Collectors.toMap(StateEntity::getStateId, StateEntity::getStateName));
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityEntity> cities = cityRepo.findByStateId(stateId);
		return cities.stream().collect(Collectors.toMap(CityEntity::getCityId, CityEntity::getCityName));
	}

	@Override
	public boolean isEmailExists(String email) {
		UserEntity user = userRepo.findByEmail(email);
		return user != null;
	}

	@Override
	public boolean saveUser(UserRegRequest userRegRequest) {
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(userRegRequest, user);
		user.setAccStatus(AppConstants.LOCKED);
		user.setPwd(generateRandomPwd());
		UserEntity savedEntity = userRepo.save(user);
		return savedEntity.getUserId() != null;
	}

	private String generateRandomPwd() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < AppConstants.PWD_LENGTH; i++) {
			sb.append(AppConstants.RANDOM_CHARS.charAt(random.nextInt(AppConstants.RANDOM_CHARS.length())));
		}
		return sb.toString();
	}

	@Override
	public boolean checkLogin(UserLoginRequest userLoginRequest) {
		UserEntity user = userRepo.findByEmailAndPwd(userLoginRequest.getEmail(), userLoginRequest.getPwd());
		return user != null;
	}
}
