package com.ngoctta.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngoctta.entity.Quality;
import com.ngoctta.mapper.ConvertTime;
import com.ngoctta.mapper.QualityMapper;
import com.ngoctta.model.QualityDto;
import com.ngoctta.model.Substance;
import com.ngoctta.model.Wqi;
import com.ngoctta.repo.QualityRepo;

@Service
public class QualityServiceImpl implements QualityService{

	@Autowired
	QualityRepo qualityRepo;
	
	@Autowired
	QualityMapper qualityMapper;
	
	@Autowired
	ConvertTime convertTime;
	
	@Override
	public Quality create(Quality quality) {
		// TODO Auto-generated method stub
		return qualityRepo.save(quality);
	}

	@Override
	public List<Quality> getDataBySubtance(Long substance_id, Long location_id) {
		// TODO Auto-generated method stub
		List<Quality>  qualities = qualityRepo.findTop10BySubstanceidAndLocationid(substance_id, location_id);
		return qualities;
	}

	@Override
	public List<Quality> getDataByLocation(Long location_id) {
		// TODO Auto-generated method stub
		List<Quality>  qualities = qualityRepo.findTop10ByLocationid(location_id);
		
		return qualities;
	}

	@Override
	public long getWQI(Long location_id) {
		List<Quality> qualities = getDataByLocation(location_id);
		int q1[] = {100, 75, 50, 25, 1};
		float BP14[] = {4, 6, 15, 25, 50};
		float BP15[] = {10, 15, 30, 50, 80};
		float BP16[] = {(float) 0.1, (float) 0.2, (float) 0.5, 1, 5};
		float BP17[] = {(float)0.1, (float)0.2, (float) 0.3, (float) 0.5, 6};
		float BP18[] = {5, 20, 30, 70, 100};
		float BP19[] = {20, 30, 50, 100, (float)100.0001};
		float BP110[] = {2500, 5000, 7500, 10000, (float) 10000.0001};
		int q2[] = {1, 25, 50, 75, 100, 100, 75, 50, 25, 1};
		float BP2[] = {(float)19.9999, 20, 50, 75, 88, 112, 125, 150, 200, (float)200.0001};
		int q3[] = {1, 50, 100, 100, 50, 1};
		float BP3[] = {(float)5.4999, (float)5.5, 6, (float)8.5, 9, (float)9.0001};
		double WQI = 0;
		float WQISIPh = 0;
		float WQISIDo = 0;
		float WQISIBod5 = 0;
		float WQISICod = 0;
		float WQISINnh4 = 0;
		float WQISIPpo4 = 0;
		float WQISITss = 0;
		float WQISIDuc = 0;
		float WQISIColiform = 0;
		float T = 0;
		for(Quality quality : qualities) {
			if(quality.getSubstance().getSubstance_id() == 2) {
				T = quality.getData();
			}
		}
		for(Quality quality : qualities) {
			if(quality.getSubstance().getSubstance_id() == 1) {
				WQISIPh = getWQISI(quality.getData(), BP3, q3, 1);
			}
			
			if(quality.getSubstance().getSubstance_id() == 3) {
				float DO =(float) (14.652 - 0.41022*T + 0.0079910*T*T - 0.000077774 *T*T*T);
				float DO_ptbh = quality.getData()/DO * 100;
				WQISIDo = getWQISI(DO_ptbh, BP2, q2, 3);
			}
			if(quality.getSubstance().getSubstance_id() == 4) {
				WQISIBod5 = getWQISI(quality.getData(), BP14, q1, 4);
			}
			if(quality.getSubstance().getSubstance_id() == 5) {
				WQISICod = getWQISI(quality.getData(), BP15, q1, 5);
			}
			if(quality.getSubstance().getSubstance_id() == 6) {
				WQISINnh4 = getWQISI(quality.getData(), BP16, q1, 6);
			}
			if(quality.getSubstance().getSubstance_id() == 7) {
				WQISIPpo4 = getWQISI(quality.getData(), BP17, q1, 7);
			}
			if(quality.getSubstance().getSubstance_id() == 8) {
				WQISITss = getWQISI(quality.getData(), BP18, q1, 8);
			}
			if(quality.getSubstance().getSubstance_id() == 9) {
				WQISIDuc = getWQISI(quality.getData(), BP19, q1, 9);
			}
			if(quality.getSubstance().getSubstance_id() == 10) {
				WQISIColiform = getWQISI(quality.getData(), BP110, q1, 10);
			}
			
		}
		

		double WQItemp = (0.2 * (WQISIDo + WQISIBod5 + WQISICod + WQISINnh4 + WQISIPpo4)
				* 0.5 *(WQISITss +  WQISIDuc) * WQISIColiform);
		WQI = WQISIPh/100 * Math.pow(WQItemp , 1.0/3);
		return Math.round(WQI);
	}
	
	public Float getWQISI(float Cp, float BP[], int q[], int type) {
		float WQISI = 0;
		int n = q.length;
		if (Cp <= BP[0]) {
			return (float) q[0];	
		}
		if (Cp >= BP[n-1]) {
			return (float) q[n-1];
		}
		for (int i = 1; i < q.length; i++) {
			if (Cp == BP[i]) {
				return (float) q[i];
			}
			if(Cp < BP[i] ) {
				if (type == 3) {
					WQISI = (q[i] - q[i-1])*(Cp - BP[i-1]) / (BP[i] - BP[i-1]) + q[i-1];
					
					return WQISI;
				} 
				if (type == 1) {
					if (Cp  > 5.5 && Cp < 6) {
						WQISI = (q[i] - q[i-1])*(Cp - BP[i-1]) / (BP[i] - BP[i-1]) + q[i-1];
						return WQISI;
					}
					if(Cp  >= 6 && Cp <= 8.5) {
						return (float) 100;
					}
					if (Cp  > 8.5 && Cp < 9) {
						WQISI = (q[i-1] - q[i])*(BP[i] - Cp) / (BP[i] - BP[i-1]) + q[i];
						return WQISI;
					}
				}
				else {
					WQISI = (q[i-1] - q[i])*(BP[i] - Cp) / (BP[i] - BP[i-1]) + q[i];
					return WQISI;
				}
				
			}	
		}
		return WQISI;
	}

	@Override
	public Wqi getAllDataWqi(Long location_id) {
		Wqi wqi = new Wqi();
		List<Substance> listSubstances = new ArrayList<Substance>();
		Long WQI = getWQI(location_id);
		wqi.setWQI(WQI);
		List<Quality> qualities = getDataByLocation(location_id);
		wqi.setTime(convertTime.LongToTime(qualities.get(1).getTime()));
		if(WQI <= 25) {
			wqi.setColor("#EE0000");
			wqi.setStatus("Ô nhiễm");
		}
		else if(WQI>=26 && WQI <= 50) {
			wqi.setColor("#FF8C00");
			wqi.setStatus("Tệ");
		}
		else if(WQI>=51 && WQI <= 75) {
			wqi.setColor("#EEEE00");
			wqi.setStatus( "Kém");
		}
		else if(WQI>=76 && WQI <= 90) {
			wqi.setColor("#00FF7F");
			wqi.setStatus("Trung bình");
		}
		else {
			wqi.setColor("#00BFFF");
			wqi.setStatus("Tốt");
		}
		for (long i = 1; i <= 10; i++) {
			List<QualityDto> listQualityDtos = new ArrayList<QualityDto>();
			listQualityDtos = qualityMapper.EntitiesToDtos(getDataBySubtance(i, location_id));
			Substance substance = new Substance();		
			substance.setId(i);		
			List<String> timeList = new ArrayList<String>();
			List<Float> dataList = new ArrayList<Float>();
			for(QualityDto qualityDto : listQualityDtos) {
				String timeString = qualityDto.getTime();
				Float dataString = qualityDto.getData();
				timeList.add(timeString);
				dataList.add(dataString);
			}
			substance.setTime(timeList);
			substance.setDataList(dataList);
			listSubstances.add(substance);
		}
		wqi.setListSubstances(listSubstances);
		
		return wqi;
	}

	@Override
	public Substance qualityToSubstance(int id, List<QualityDto> dto) {
		Substance substance = new Substance();
		substance.setId((long) id);	
		List<String> timeList = new ArrayList<String>();
		List<Float> dataList = new ArrayList<Float>();
		for(QualityDto qualityDto : dto) {
			String timeString = qualityDto.getTime();
			float dataString = qualityDto.getData();
			timeList.add(timeString);
			dataList.add(dataString);
		}
		substance.setTime(timeList);
		substance.setDataList(dataList);
		return substance;
	}

	


}
