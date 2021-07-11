package com.app.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRegRequest {

	private String firstName;

	private String lastName;

	private String email;
	
	private Long phno;

	private String gender;

	private LocalDate dob;

	private Integer countryId;

	private Integer stateId;

	private Integer cityId;

}
