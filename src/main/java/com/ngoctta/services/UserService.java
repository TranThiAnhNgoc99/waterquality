package com.ngoctta.services;

import java.util.List;
import java.util.Optional;

import com.ngoctta.entity.User;
import com.ngoctta.model.UserDto;
import com.ngoctta.model.UserInput;
import com.ngoctta.request.RegisterRequest;

public interface UserService {

	User saveUser(User user);
	
	User createUser(RegisterRequest registerRequest);
	
	void deleteUser(Long user_id);
	
	User updateUser(Long user_id, UserInput userInput);
	
	User getuser(String username);
	
	Optional<User> getUser(String username);

	Optional<User> getUserById(Long user_id);
	
	List<User> getUsers();
	
	Boolean checkLogin(String username, String password);
}
