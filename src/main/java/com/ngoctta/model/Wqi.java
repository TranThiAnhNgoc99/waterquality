package com.ngoctta.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wqi {
	public Long WQI;
	public String time;
	public String color;
	public String status;
	public List<Substance> listSubstances;
}
