package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.constants.AppConstants;
import com.app.dto.UserLoginRequest;
import com.app.dto.UserRegRequest;
import com.app.dto.UserUnlockRequest;
import com.app.entity.CityEntity;
import com.app.entity.CountryEntity;
import com.app.entity.StateEntity;
import com.app.entity.UserEntity;
import com.app.repository.CityRepo;
import com.app.repository.CountryRepo;
import com.app.repository.StateRepo;
import com.app.repository.UserRepo;
import com.app.utils.EmailUtils;
import com.app.utils.PwdUtils;

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
		user.setPwd(PwdUtils.encode(PwdUtils.generateRandomPwd()));
		UserEntity savedEntity = userRepo.save(user);

		if (savedEntity.getUserId() != null) {
			String mailBody = EmailUtils.readFile(AppConstants.USER_REG_BODY_FILE);
			mailBody = mailBody.replace(AppConstants.FNAME, savedEntity.getFirstName());
			mailBody = mailBody.replace(AppConstants.LNAME, savedEntity.getLastName());
			mailBody = mailBody.replace(AppConstants.TEMP_PWD, savedEntity.getPwd());
			mailBody = mailBody.replace(AppConstants.EMAIL, savedEntity.getEmail());

			EmailUtils.sendEmail(savedEntity.getEmail(), AppConstants.USER_REG_SUBJECT, mailBody);
		}
		return savedEntity.getUserId() != null;
	}

	@Override
	public String checkLogin(UserLoginRequest userLoginRequest) {
		String encodePwd = PwdUtils.encode(userLoginRequest.getPwd());
		UserEntity user = userRepo.findByEmailAndPwd(userLoginRequest.getEmail(), encodePwd);
		if (user != null) {
			String accStatus = user.getAccStatus();
			if (AppConstants.LOCKED.equals(accStatus)) {
				return AppConstants.ACC_LOCKED_MSG;
			} else {
				return AppConstants.LOGIN_SUCCESS_MSG;
			}
		} else {
			return AppConstants.INVALID_LOGIN_MSG;
		}
	}

	@Override
	public boolean unlockUser(UserUnlockRequest userUnlockRequest) {
		String encodePwd = PwdUtils.encode(userUnlockRequest.getTempPwd());
		UserEntity user = userRepo.findByEmailAndPwd(userUnlockRequest.getEmail(), encodePwd);
		if (user != null) {
			user.setAccStatus(AppConstants.UNLOCKED);
			user.setPwd(PwdUtils.encode(userUnlockRequest.getNewPwd()));
			userRepo.save(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean forgetPwd(String email) {
		UserEntity user = userRepo.findByEmail(email);
		if (user != null) {
			user.setPwd(PwdUtils.encode(PwdUtils.generateRandomPwd()));
			String mailBody = EmailUtils.readFile(AppConstants.USER_FORGET_BODY_FILE);
			mailBody = mailBody.replace(AppConstants.FNAME, user.getFirstName());
			mailBody = mailBody.replace(AppConstants.LNAME, user.getLastName());
			mailBody = mailBody.replace(AppConstants.TEMP_PWD, user.getPwd());
			mailBody = mailBody.replace(AppConstants.EMAIL, user.getEmail());

			EmailUtils.sendEmail(user.getEmail(), AppConstants.USER_FORGET_SUBJECT, mailBody);
		}
		return false;
	}
}
