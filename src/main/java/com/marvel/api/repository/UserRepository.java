package com.marvel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marvel.api.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
