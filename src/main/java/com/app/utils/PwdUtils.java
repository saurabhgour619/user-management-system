package com.app.utils;

import java.util.Base64;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.app.constants.AppConstants;

@Component
public class PwdUtils {

	public static String generateRandomPwd() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < AppConstants.PWD_LENGTH; i++) {
			sb.append(AppConstants.RANDOM_CHARS.charAt(random.nextInt(AppConstants.RANDOM_CHARS.length())));
		}
		return sb.toString();
	}

	public static String encode(String input) {
		return new String(Base64.getEncoder().encode(input.getBytes()));
	}

	public static String decode(String input) {
		return new String(Base64.getDecoder().decode(input.getBytes()));
	}
}
