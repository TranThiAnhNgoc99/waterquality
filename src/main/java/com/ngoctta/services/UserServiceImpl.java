package com.ngoctta.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ngoctta.entity.MyUserDetails;
import com.ngoctta.entity.User;
import com.ngoctta.exception.BadRequestException;
import com.ngoctta.exception.NotFoundException;
import com.ngoctta.model.UserInput;
import com.ngoctta.repo.UserRepo;
import com.ngoctta.request.RegisterRequest;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
    UserRepo userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Optional<User> user = userRepository.findByUsername(username);

	        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
	        return user.map(MyUserDetails::new).get();
	}

	@Override
	public User saveUser(User user) {
		log.info("Saving new user {} to database", user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getuser(String username) {
		log.info("Fetching user {}", username);
		return userRepository.findByusername(username);
	}

	@Override
	public List<User> getUsers() {
		log.info("Fetching all users");
		return (List<User>) userRepository.findAll();
	}

	@Override
	public void deleteUser(Long user_id) {
		Optional<User> user = userRepository.findById(user_id);
		if (!user.isPresent()) {
			throw new NotFoundException("This user is not found.");
		}
		log.info("Delete user has id {}", user_id);
		userRepository.deleteById(user_id);
	}

	@Override
	public Optional<User> getUser(String username) {
		log.info("Fetching user {}", username);
		return userRepository.findByUsername(username);
	}

	@Override
	public Boolean checkLogin(String username, String password) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
			log.info("Fetching user {}", username);
			return true;
		}
		log.info("Cannot fetching user");
		return false;
	}

	@Override
	public Optional<User> getUserById(Long user_id) {
		log.info("Fetching user id {}", user_id);
		return userRepository.findById(user_id);
	}

	@Override
	public User createUser(RegisterRequest registerRequest) {
		Optional<User> user = userRepository.findByUsername(registerRequest.getUsername());
		if(user.isPresent()) {
			throw new BadRequestException("User has been exist");
		}
		User user2 = new User();
		user2.setUsername(registerRequest.getUsername());
		user2.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		
		return userRepository.save(user2);
	}

	@Override
	public User updateUser(Long user_id, UserInput userInput) {
		Optional<User> uOptional = userRepository.findById(user_id);
		if (!uOptional.isPresent()) {
			throw new NotFoundException("This user is not found");
		}
		if (!uOptional.get().getUsername().equals(userInput.getUsername())) {
			if (userRepository.findByUsername(userInput.getUsername()).isPresent()) {
				throw new BadRequestException("Username has been exist");
			}
		}
		User user = uOptional.get();
		user.setUsername(userInput.getUsername());
		user.setPassword(passwordEncoder.encode(userInput.getPassword()));
		return userRepository.save(user);
	}
	

}
