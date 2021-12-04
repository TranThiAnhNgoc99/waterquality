package com.ngoctta.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngoctta.entity.Location;
import com.ngoctta.exception.BadRequestException;
import com.ngoctta.exception.NotFoundException;
import com.ngoctta.repo.LocationRepo;

@Service
public class LocationSeviceImpl implements LocationSevice{

	private static final Logger log = LoggerFactory.getLogger(LocationSeviceImpl.class);
	@Autowired
	LocationRepo locationRepo;
	
	@Override
	public Location saveLocation(Location location) {
		Optional<Location> oldLocation = locationRepo.findByLocationName(location.getLocationName());
		if(oldLocation.isPresent()) {
			throw new NotFoundException("This Location has been exist");
		}
		log.info("Saving new location {} to database", location.getLocationName());
		return locationRepo.save(location);
	}

	@Override
	public void deleteLocation(Long id) {
		Optional<Location> oldLocation = locationRepo.findById(id);
		if(!oldLocation.isPresent()) {
			throw new NotFoundException("Not found this Location");
		}
		log.info("Delete location {}", locationRepo.findById(id).get().getLocationName());
		locationRepo.deleteById(id);
		
	}

	@Override
	public Optional<Location> getLocation(String locationName) {
		log.info("Fetching location {}", locationName);
		return locationRepo.findByLocationName(locationName);
	}

	@Override
	public List<Location> getLocations() {
		log.info("Fetching all loaction");
		return locationRepo.findAll();
	}

	@Override
	public Location updateLocation(Location location, Long id) {
		Optional<Location> oldLocation = locationRepo.findById(id);
		if(!oldLocation.isPresent()) {
			throw new NotFoundException("Not found this Location");
		}
		if(!location.getLocationName().equals(oldLocation.get().getLocationName())) {
			if (locationRepo.findByLocationName(location.getLocationName()).isPresent()) {
				throw new BadRequestException("This Location name is existing");
			}
		}
		Location newLocation = oldLocation.get();
		newLocation.setLocationName(location.getLocationName());
		return locationRepo.save(newLocation);
	}

	@Override
	public Optional<Location> getLocationById(Long location_id) {
		Optional<Location> location = locationRepo.findById(location_id);
		if (!location.isPresent()) {
			throw new NotFoundException("Not found this Location");
		}
		
		log.info("Fetching location id {}", location_id);
		return location;
	}

}
