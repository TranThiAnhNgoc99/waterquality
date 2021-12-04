package com.ngoctta.mapper;

import java.util.List;

import com.ngoctta.entity.Location;
import com.ngoctta.model.LocationDto;


public interface LocationMapper{
	public LocationDto EntityToDto(Location entity);
	public Location DtoToEntity(LocationDto dto);
	List<LocationDto> EntitiesToDtos(List<Location> entities);
}
