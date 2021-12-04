package com.ngoctta.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ngoctta.entity.Location;
import com.ngoctta.entity.User;
import com.ngoctta.services.LocationSevice;
import com.ngoctta.services.UserService;

@Controller
@RequestMapping("")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	
	@Autowired
	UserService userService;
	
	@Autowired
	LocationSevice locationService;
	
	@GetMapping( "/analysis-center")
	public String admin(Model model) {
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("locations", locationService.getLocations());
	    return "admin";
	}
	
	@GetMapping("/add-location-form")
	public String addLocationForm() {
		return "add-location";
	}
	
	@PostMapping("/add-location")
	public String addLocation(Location location, BindingResult result, Model model,
								@RequestParam("locationName") String locationName,
								HttpServletRequest request) {
		Optional<Location> locationExists = locationService.getLocation(location.getLocationName());
		if (locationExists.isPresent()) {
			model.addAttribute("alreadyRegisteredMessage",
					"Oops!  There is already a location registered with the location name provided.");
			log.info("There is already a location registered with the location name provided");
			result.reject("locationName");
			return "add-location";
		}
		if (location.getLocationName().isEmpty()) {
			model.addAttribute("LOCATIONNAMENULL","Please enter location name");
			log.info("enter location name");
			result.reject("locationName");
			return "add-location";
		}
		if (result.hasErrors()) {
			location.setLocationName(locationName);
			log.info("error");
			return "add-location";
		}
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info("bthg");
		locationService.saveLocation(location);
		return "redirect:/analysis-center";
	}
	
	@GetMapping("/edit-location-form/{location_id}")
	public String editLocationForm(@PathVariable("location_id") Long location_id, Model model) {
		//User user = userService.getUser(username)
		Location location = locationService.getLocationById(location_id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid location_id:" + location_id));
		model.addAttribute("location", location);
		return "edit-location";
	}
	
	@PostMapping("/edit-location/{location_id}")
	public String editLocation(@PathVariable("location_id") Long location_id, @Valid Location location, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			location.setLocation_id(location_id);
			return "redirect:/edit-location-form";
		}
		locationService.saveLocation(location);
		return "redirect:/analysis-center";
	}
	
	@GetMapping("/delete-location/{location_id}")
	public String deleteLocation(@PathVariable("location_id") Long location_id, Model model) {
		Location location = locationService.getLocationById(location_id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid location_id:" + location_id));
		locationService.deleteLocation(location_id);;
		return "redirect:/analysis-center";
	}
	
	@GetMapping("/add-admin-form")
	public String addAdminForm() {
		return "add-admin";
	}
	
	@PostMapping("/add-admin")
	public String addAdmin(User user, BindingResult result, Model model, @RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletRequest request) {
		User userExists = userService.getuser(user.getUsername());
		if (userExists != null) {
			model.addAttribute("alreadyRegisteredMessage",
					"Oops!  There is already a user registered with the username provided.");
			result.reject("username");
			return "add-admin";
		}
		if (user.getPassword().isEmpty()) {
			model.addAttribute("PASSWORDNULL","Please enter password");
			result.reject("password");
			return "add-admin";
		}
		if (user.getUsername().isEmpty()) {
			model.addAttribute("USERNAMENULL","Please enter username");
			result.reject("username");
			return "add-admin";
		}
		if (result.hasErrors()) {
			user.setUsername(username);
			return "add-admin";
		}
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.saveUser(user);
		return "redirect:/analysis-center";
	}
	
	@GetMapping("/edit-admin-form/{user_id}")
	public String editAdminForm(@PathVariable("user_id") Long user_id, Model model) {
		User user = userService.getUserById(user_id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user_id:" + user_id));
		model.addAttribute("user", user);
		return "edit-admin";
	}
	
	@PostMapping("/edit-admin/{user_id}")
	public String editAdmin(@PathVariable("user_id") Long user_id, @Valid User user, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			user.setUser_id(user_id);;
			return "redirect:/edit-admin-form";
		}
		userService.saveUser(user);
		return "redirect:/analysis-center";
	}

	@GetMapping("/delete-admin/{user_id}")
	public String deleteUser(@PathVariable("user_id")Long user_id, Model model) {
		User user = userService.getUserById(user_id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user_id:" + user_id));
		userService.deleteUser(user_id);
		return "redirect:/analysis-center";
	}
	
}
