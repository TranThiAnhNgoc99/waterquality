package com.ngoctta.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ngoctta.entity.Location;
import com.ngoctta.model.LocationDto;

@Component
public class LocationMapperImpl implements LocationMapper {

	@Override
	public LocationDto EntityToDto(Location entity) {
		LocationDto locationDto = new LocationDto();
		locationDto.setLocation_id(entity.getLocation_id());
		locationDto.setLocationName(entity.getLocationName());
		return locationDto;
	}

	@Override
	public Location DtoToEntity(LocationDto dto) {
		Location location = new Location();
		location.setLocation_id(dto.getLocation_id());
		location.setLocationName(dto.getLocationName());
		return location;
	}

	@Override
	public List<LocationDto> EntitiesToDtos(List<Location> entities) {
		List<LocationDto> locationDtos = new ArrayList<LocationDto>();
		for(Location entity : entities) {
			LocationDto locationDto = new LocationDto();
			locationDto.setLocation_id(entity.getLocation_id());
			locationDto.setLocationName(entity.getLocationName());
			locationDtos.add(locationDto);
		}
		return locationDtos;
	}

}
