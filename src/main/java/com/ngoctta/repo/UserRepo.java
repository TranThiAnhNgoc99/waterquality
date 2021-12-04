package com.ngoctta.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ngoctta.entity.User;

public interface UserRepo extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
	User findByusername(String username);
}
