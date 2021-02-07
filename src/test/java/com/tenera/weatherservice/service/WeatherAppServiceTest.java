package com.tenera.weatherservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tenera.weatherservice.dto.HistoryReportDto;
import com.tenera.weatherservice.dto.WeatherReportDto;
import com.tenera.weatherservice.exception.OpenWeatherApiException;
import com.tenera.weatherservice.facade.OpenWeatherApiFacade;

/**
 * Unit test class for WeatherAppService class
 * 
 * @author Satya Kolipaka 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class WeatherAppServiceTest {

	@Autowired
	private WeatherAppService weatherAppService;

	@MockBean
	private OpenWeatherApiFacade openWeatherApiFacadeMock;

	@Test
	void testFetchWeatherReportSuccess() {
		WeatherReportDto weatherReportDto = buildWeatherReportDto();
		when(openWeatherApiFacadeMock.retrieveWeatherReport("Frankfurt")).thenReturn(weatherReportDto);

		WeatherReportDto response = weatherAppService.fetchWeatherReport("Frankfurt");
		assertEquals(997.0, response.getPressure());
		assertEquals(-0.4, response.getTemp());
		assertEquals(true, response.isUmbrella());
	}

	@Test
	void testFetchWeatherReportError() {
		when(openWeatherApiFacadeMock.retrieveWeatherReport("csdhldk"))
				.thenThrow(new OpenWeatherApiException("city not found", 404));

		Assertions.assertThrows(OpenWeatherApiException.class, () -> {
			weatherAppService.fetchWeatherReport("csdhldk");
		});
	}

	@Test
	void testHistoryWeatherReportSuccess() {
		List<WeatherReportDto> weatherReportDtoList = buildWeatherReportDtoList();
		when(openWeatherApiFacadeMock.retrieveHistoryWeatherReport("Frankfurt")).thenReturn(weatherReportDtoList);
		
		HistoryReportDto response = weatherAppService.historyWeatherReport("Frankfurt");
		assertEquals(998.8, response.getAvgPressure());
		assertEquals(-2.57, response.getAvgTemp());
		assertEquals(5, response.getHistory().size());
		
	}
	
	@Test
	void testHistoryWeatherReportError() {
		when(openWeatherApiFacadeMock.retrieveHistoryWeatherReport("csdhldk"))
				.thenThrow(new OpenWeatherApiException("city not found", 404));

		Assertions.assertThrows(OpenWeatherApiException.class, () -> {
			weatherAppService.historyWeatherReport("csdhldk");
		});
	}

	
	private WeatherReportDto buildWeatherReportDto() {
		return new WeatherReportDto(-0.4, 997.0, true);
	}
	
	private List<WeatherReportDto> buildWeatherReportDtoList() {
		
		List<WeatherReportDto> weatherReportDtoList =  new ArrayList<>();
		weatherReportDtoList.add(new WeatherReportDto(-0.29,998.0, true));
		weatherReportDtoList.add(new WeatherReportDto(-1.27,999.0, false));
		weatherReportDtoList.add(new WeatherReportDto(-2.92,999.0, false));
		weatherReportDtoList.add(new WeatherReportDto(-3.88,999.0, false));
		weatherReportDtoList.add(new WeatherReportDto(-4.49,999.0, false));
		
		return weatherReportDtoList;
	}
	
	

}
