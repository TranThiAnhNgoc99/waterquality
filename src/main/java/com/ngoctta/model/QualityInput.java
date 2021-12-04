package com.ngoctta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityInput {
	public Long quality_id;
	public Float data;
	public Long time;
	public Long location_id;
	public Long subtance_id;
}
