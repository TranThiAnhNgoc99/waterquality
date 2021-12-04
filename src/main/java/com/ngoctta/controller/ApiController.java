package com.ngoctta.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngoctta.entity.Location;
import com.ngoctta.entity.MyUserDetails;
import com.ngoctta.entity.Quality;
import com.ngoctta.entity.User;
import com.ngoctta.exception.InternalServerException;
import com.ngoctta.exception.NotFoundException;
import com.ngoctta.exception.UnauthorizedException;
import com.ngoctta.mapper.LocationMapper;
import com.ngoctta.mapper.QualityMapper;
import com.ngoctta.mapper.UserMapper;
import com.ngoctta.model.LocationDto;
import com.ngoctta.model.LoginResponseDTO;
import com.ngoctta.model.QualityDto;
import com.ngoctta.model.QualityInput;
import com.ngoctta.model.UserDto;
import com.ngoctta.model.UserInput;
import com.ngoctta.model.Wqi;
import com.ngoctta.request.LoginRequest;
import com.ngoctta.request.RegisterRequest;
import com.ngoctta.response.LoginResponse;
import com.ngoctta.security.JwtUtils;
import com.ngoctta.services.LocationSevice;
import com.ngoctta.services.QualityService;
import com.ngoctta.services.UserService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	UserService userService;
	
	@Autowired
	LocationSevice locationService;
	
	@Autowired
	QualityService qualityService;
	
	@Autowired
	LocationMapper locationMapper;
	
	@Autowired
	QualityMapper qualityMapper;
	
	@Autowired
	UserMapper userMapper;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;
	
	/*---------LOCATION--------*/
	
	@GetMapping("/location")
	public ResponseEntity<List<LocationDto>> getAllLocation(){
		return ResponseEntity.ok().body(locationMapper.EntitiesToDtos(locationService.getLocations()));
	}
	
	@GetMapping("/location/{location_id}")
	public ResponseEntity<LocationDto> getLocation(@PathVariable("location_id") Long location_id){
		return ResponseEntity.ok().body(locationMapper.EntityToDto(locationService.getLocationById(location_id).get()));
	}
	
	@PostMapping("/location")
	public ResponseEntity<LocationDto> createLocation(@RequestParam String locationName){
		Location location = new Location();
		location.setLocationName(locationName);
		return ResponseEntity.ok().body(locationMapper.EntityToDto(locationService.saveLocation(location)));
	}
	
	@PutMapping("/location/{location_id}")
	public ResponseEntity<LocationDto> updateLocation(@PathVariable("location_id") Long location_id, @Valid @RequestBody Location location){
		return ResponseEntity.ok().body(locationMapper.EntityToDto(locationService.updateLocation(location, location_id)));
	}
	
	@DeleteMapping("/location/{location_id}")
	public ResponseEntity<String> deleteLocation(@PathVariable("location_id") Long location_id){
		locationService.deleteLocation( location_id);
		return ResponseEntity.ok().body("Delete Location Successfully!");
	}
	
	
	/*---------QUALITY--------*/
	
	@GetMapping("/quality/{location_id}")
	public ResponseEntity<Wqi> getDataByLocationID(@PathVariable Long location_id){
		return ResponseEntity.ok().body(qualityService.getAllDataWqi(location_id));
	}
	
	@PostMapping("/quality")
	public ResponseEntity<QualityDto> createQuality (@Valid @RequestBody QualityInput qualityDto){
		Quality quality = qualityService.create(qualityMapper.InputToEntity(qualityDto));
		return ResponseEntity.ok().body(qualityMapper.EntityToDto(quality));
	}
	
	
	/*-----------USER---------*/

	@PostMapping("/login")
	public LoginResponse authenticateUser(@Valid @RequestBody( required = true) LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
			List<String> roles = myUserDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return LoginResponse
					.success(new LoginResponseDTO(jwt, myUserDetails.getId(), myUserDetails.getUsername(), roles));
		} catch (AuthenticationException e) {
			if (e instanceof BadCredentialsException) {
				throw new UnauthorizedException("Unauthorized");
			}
			throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@GetMapping("/user")
	public ResponseEntity<List<UserDto>> getAllUsers() {

		List<User> listUsers = (List<User>) userService.getUsers();
		List<UserDto> dtos = userMapper.EntitiesToDtos(listUsers);
		// return ResponseEntity.ok().body(dtos);
		return ResponseEntity.ok().body(dtos);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<UserDto> getUser(@Parameter(example = "admin1") @PathVariable(value = "username") String username) {
		User user = userService.getUser(username)
				.orElseThrow(() -> new NotFoundException("Invalid user username: " + username));
		UserDto userDTO = userMapper.EntityToDto(user);
		return ResponseEntity.ok().body(userDTO);
	}
	
	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser (@Valid @RequestBody RegisterRequest registerRequest){
		
		return ResponseEntity.ok().body(userMapper.EntityToDto(userService.createUser(registerRequest)));
	}
	
	@PutMapping("/user/{user_id}")
	public ResponseEntity<UserDto> updateUser (@PathVariable Long user_id, UserInput userInput){
		return ResponseEntity.ok().body(userMapper.EntityToDto(userService.updateUser(user_id, userInput)));
	}
	
	@DeleteMapping("/user/{user_id}")
	public ResponseEntity<String> deleteUser (@PathVariable Long user_id){
		userService.deleteUser(user_id);
		return ResponseEntity.ok().body("Delete user successfully.");
	}
}
