package com.ngoctta.mapper;

import org.springframework.stereotype.Component;

@Component
public class ConvertTimeImpl implements ConvertTime{

	@Override
	public String LongToTime(Long timeLong) {
		String timeString = timeLong.toString();
		String nam 		= timeString.substring(0, 4);
		String thang 	= timeString.substring(4, 6);
		String ngay 	= timeString.substring(6, 8);
		String gio 		= timeString.substring(8, 10);
		String phut 	= timeString.substring(10, 12);
		String giay 	= timeString.substring(12, 14);
		String timeModel = nam + '-' + thang + '-' + ngay +
				"   " + gio + ':' + phut + ':' + giay;
		return timeModel;
	}
}
