package com.app.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.constants.AppConstants;
import com.app.dto.UserUnlockRequest;
import com.app.service.UserService;

@RestController
public class UserUnlockController {

	private UserService userService;
	
	public String unlockUser(@RequestBody UserUnlockRequest userUnlockRequest) {
		if(!userUnlockRequest.getNewPwd().equals(userUnlockRequest.getConfirmNewPwd())) {
			return AppConstants.MISMATCH_PWD;
		}
		boolean isUnlocked = userService.unlockUser(userUnlockRequest);
		if(isUnlocked) {
			return AppConstants.ACC_UNLOCKED;
		}
		return AppConstants.INVALID_TEMP_PWD;
	}
}
