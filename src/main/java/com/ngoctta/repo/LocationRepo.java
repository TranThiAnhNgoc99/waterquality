package com.ngoctta.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngoctta.entity.Location;


public interface LocationRepo extends JpaRepository<Location, Long>{
	Optional<Location> findByLocationName(String locationName);
	Location findBylocationName(String locationName);
}
