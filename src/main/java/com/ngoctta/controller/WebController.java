package com.ngoctta.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ngoctta.entity.Location;
import com.ngoctta.entity.Quality;
import com.ngoctta.mapper.ConvertTime;
import com.ngoctta.mapper.QualityMapper;
import com.ngoctta.model.QualityDto;
import com.ngoctta.model.Substance;
import com.ngoctta.services.LocationSevice;
import com.ngoctta.services.QualityService;
import com.ngoctta.services.UserService;

@Controller
public class WebController {
	
	private static final Logger log = LoggerFactory.getLogger(WebController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	LocationSevice locationService;
	
	@Autowired
	QualityService qualityService;
	
	@Autowired
	ConvertTime convertTime;
	
	@Autowired
	QualityMapper qualityMapper;

	// Don nhan request GET
	@GetMapping( name="/") // nguoi dung request toi dia chi "/"
	public String home(@RequestParam(defaultValue = "1") Long location_id, Model model) {
		
		List<Location> listLocations = locationService.getLocations();
		model.addAttribute("listLocations", listLocations);
		log.info("location: {}", location_id);
		
		List<Quality> qualities = qualityService.getDataByLocation(location_id);
		Long timeLong = qualities.get(1).getTime();
		String timeString = convertTime.LongToTime(timeLong);
		model.addAttribute("time", timeString);
		
		long WQI = qualityService.getWQI(location_id);
		model.addAttribute("wqi", WQI);
		
		if(WQI <= 25) {
			model.addAttribute("wqiColor", "#EE0000");
			model.addAttribute("wqiStatus", "Ô nhiễm");
		}
		else if(WQI>=26 && WQI <= 50) {
			model.addAttribute("wqiColor", "#FF8C00");
			model.addAttribute("wqiStatus", "Tệ");
		}
		else if(WQI>=51 && WQI <= 75) {
			model.addAttribute("wqiColor", "#EEEE00");
			model.addAttribute("wqiStatus", "Kém");
		}
		else if(WQI>=76 && WQI <= 90) {
			model.addAttribute("wqiColor", "#00FF7F");
			model.addAttribute("wqiStatus", "Trung bình");
		}
		else {
			model.addAttribute("wqiColor", "#00BFFF");
			model.addAttribute("wqiStatus", "Tốt");
		}
		
		List<QualityDto> pHQualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 1, location_id));
		Substance pHSub = qualityService.qualityToSubstance(1, pHQualities);
		model.addAttribute("pHSub", pHSub);
		List<QualityDto> t0Qualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 2, location_id));
		Substance t0Sub = qualityService.qualityToSubstance(2, t0Qualities);
		model.addAttribute("t0Sub", t0Sub);
		List<QualityDto> DOQualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 3, location_id));
		Substance DOSub = qualityService.qualityToSubstance(3, DOQualities);
		model.addAttribute("DOSub", DOSub);
		List<QualityDto> BOD5Qualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 4, location_id));
		Substance BOD5Sub = qualityService.qualityToSubstance(4, BOD5Qualities);
		model.addAttribute("BOD5Sub", BOD5Sub);
		List<QualityDto> CODQualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 5, location_id));
		Substance CODSub = qualityService.qualityToSubstance(5, CODQualities);
		model.addAttribute("CODSub", CODSub);
		List<QualityDto> NNH4Qualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 6, location_id));
		Substance NNH4Sub = qualityService.qualityToSubstance(6, NNH4Qualities);
		model.addAttribute("NNH4Sub", NNH4Sub);
		List<QualityDto> PPO4Qualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 7, location_id));
		Substance PPO4Sub = qualityService.qualityToSubstance(7, PPO4Qualities);
		model.addAttribute("PPO4Sub", PPO4Sub);
		List<QualityDto> TSSQualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 8, location_id));
		Substance TSSSub = qualityService.qualityToSubstance(8, TSSQualities);
		model.addAttribute("TSSSub", TSSSub);
		List<QualityDto> DucQualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 9, location_id));
		Substance DucSub = qualityService.qualityToSubstance(9, DucQualities);
		model.addAttribute("DucSub", DucSub);
		List<QualityDto> ColiformQualities = qualityMapper.EntitiesToDtos(qualityService.getDataBySubtance((long) 10, location_id));
		Substance ColiformSub = qualityService.qualityToSubstance(10, ColiformQualities);
		model.addAttribute("ColiformSub", ColiformSub);
		return "home"; // tra ve file index.html
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping( "/logout")
	public String logout() {
	    //model.addAttribute("user", getPrincipal());
	    return "login";
	}
	
	@PostMapping("/checklogin")
	public RedirectView checkLogin(@RequestParam("username") String username,
			@RequestParam("password") String password, RedirectAttributes redirectAttributes) {
		if(userService.checkLogin(username, password)) {
				return new RedirectView("/analysis-center");
			//}
		
		}
		//return "redirect:login?error=Username or Password is not exist";
		return new RedirectView("login?error=Username or Password is not exist");
		
	}
}
