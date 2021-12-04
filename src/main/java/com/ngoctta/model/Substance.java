package com.ngoctta.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Substance {
	public Long id;
	public List<String> time;
	public List<Float> dataList;
}
