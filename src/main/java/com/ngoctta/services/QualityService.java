package com.ngoctta.services;

import java.util.List;

import com.ngoctta.entity.Quality;
import com.ngoctta.model.QualityDto;
import com.ngoctta.model.Substance;
import com.ngoctta.model.Wqi;

public interface QualityService {
	
	Quality create(Quality quality);
	
	List<Quality> getDataBySubtance(Long substance_id, Long location_id);

	List<Quality> getDataByLocation(Long location_id);
	
	long getWQI(Long location_id);
	
	Wqi getAllDataWqi(Long location_id);

	Substance qualityToSubstance(int id, List<QualityDto> dto);
}
