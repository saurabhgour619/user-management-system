package com.app.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email" , unique = true)
	private String email;
	
	@Column(name = "contact_no")
	private Long phno;

	@Column(name = "pwd")
	private String pwd;

	@Column(name = "gender")
	private String gender;

	@Column(name = "dob")
	private LocalDate dob;

	@Column(name = "acc_status")
	private String accStatus;

	@Column(name = "country_id")
	private Integer countryId;

	@Column(name = "state_id")
	private Integer stateId;

	@Column(name = "city_id")
	private Integer cityId;

	@Column(name = "created_dt", updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;

	@Column(name = "updated_dt", insertable = false)
	@UpdateTimestamp
	private LocalDate updatedDate;

}