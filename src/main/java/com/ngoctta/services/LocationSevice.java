package com.ngoctta.services;

import java.util.List;
import java.util.Optional;

import com.ngoctta.entity.Location;

public interface LocationSevice {
	
	Location saveLocation(Location location);
	
	void deleteLocation(Long id);
	
	Location updateLocation(Location location, Long id);
	
	Optional<Location> getLocation(String locationName);
	
	Optional<Location> getLocationById(Long id);

	List<Location> getLocations();
}
