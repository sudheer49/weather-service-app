package com.tenera.weatherservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenera.weatherservice.dto.WeatherReportDto;
import com.tenera.weatherservice.facade.OpenWeatherApiFacade;

/**
 * Service class which is having all the methods related to WeatherAppController
 * 
 * @author Satya_Kolipaka
 *
 */
@Service
public class WeatherAppService {

	@Autowired
	private OpenWeatherApiFacade openWeatherApiFacade;

	/**
	 * Method to fetch the weather report
	 * 
	 * @param location
	 * @return
	 */
	public WeatherReportDto fetchWeatherReport(String city) {
		return openWeatherApiFacade.retrieveWeatherReport(city);
	}

}
