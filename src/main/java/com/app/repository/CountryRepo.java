package com.app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Serializable> {

}
