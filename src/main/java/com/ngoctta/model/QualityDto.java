package com.ngoctta.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QualityDto {
	public Long quality_id;
	public Float data;
	public String time;
	public Long location_id;
	public Long subtance_id;
}
