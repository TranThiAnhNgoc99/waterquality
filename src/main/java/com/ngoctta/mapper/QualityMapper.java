package com.ngoctta.mapper;

import java.util.List;

import com.ngoctta.entity.Quality;
import com.ngoctta.model.QualityDto;
import com.ngoctta.model.QualityInput;

public interface QualityMapper {
	public QualityDto EntityToDto(Quality entity);
	public Quality InputToEntity(QualityInput dto);
	List<QualityDto> EntitiesToDtos(List<Quality> entities);

}
