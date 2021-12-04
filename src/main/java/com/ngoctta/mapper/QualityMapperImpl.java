package com.ngoctta.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngoctta.entity.Quality;
import com.ngoctta.model.QualityDto;
import com.ngoctta.model.QualityInput;

@Component
public class QualityMapperImpl implements QualityMapper{

	@Autowired
	ConvertTime convertTime;
	@Override
	public QualityDto EntityToDto(Quality entity) {
		QualityDto qualityDto = new QualityDto();
		qualityDto.setQuality_id(entity.getQuality_id());
		qualityDto.setData(entity.getData());
		qualityDto.setTime(convertTime.LongToTime(entity.getTime()));
		qualityDto.setLocation_id(entity.getLocation().getLocation_id());
		qualityDto.setSubtance_id(entity.getSubstance().getSubstance_id());
		return qualityDto;
	}

	@Override
	public Quality InputToEntity(QualityInput dto) {
		Quality quality = new Quality();
		quality.setQuality_id(dto.getQuality_id());
		quality.setData(dto.getData());
		quality.setTime(dto.getTime());
		quality.getLocation().setLocation_id(dto.getLocation_id());
		quality.getSubstance().setSubstance_id(dto.getSubtance_id());
		return quality;
	}

	@Override
	public List<QualityDto> EntitiesToDtos(List<Quality> entities) {
		List<QualityDto> listDto = new ArrayList<QualityDto>();
		for(Quality entity : entities) {
			QualityDto qualityDto = new QualityDto();
			qualityDto.setQuality_id(entity.getQuality_id());
			qualityDto.setData(entity.getData());
			qualityDto.setTime(convertTime.LongToTime(entity.getTime()));
			qualityDto.setLocation_id(entity.getLocation().getLocation_id());
			qualityDto.setSubtance_id(entity.getSubstance().getSubstance_id());
			listDto.add(qualityDto);
		}
		return listDto;
	}

}
