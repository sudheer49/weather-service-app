package com.tenera.weatherservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenera.weatherservice.dto.HistoryReportDto;
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
	 * Method to fetch the weather report from external API
	 * 
	 * @param location
	 * @return
	 */
	public WeatherReportDto fetchWeatherReport(String city) {
		return openWeatherApiFacade.retrieveWeatherReport(city);
	}

	/**
	 * Method to fetch last five weather reports and calculate average temperature
	 * and pressure
	 * 
	 * @param city
	 * @return
	 */
	public HistoryReportDto historyWeatherReport(String city) {
		HistoryReportDto historyReportDto = new HistoryReportDto();
		List<WeatherReportDto> weatherReportDtoList = openWeatherApiFacade.retrieveHistoryWeatherReport(city);

		Double avgTemp = weatherReportDtoList.stream().mapToDouble(WeatherReportDto::getTemp).average().getAsDouble();

		Double avgPressure = weatherReportDtoList.stream().mapToDouble(WeatherReportDto::getPressure).average()
				.getAsDouble();

		historyReportDto.setAvgPressure(avgPressure);
		historyReportDto.setAvgTemp(avgTemp);
		historyReportDto.setHistory(weatherReportDtoList);

		return historyReportDto;
	}

}
