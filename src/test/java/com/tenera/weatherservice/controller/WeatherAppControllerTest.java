package com.tenera.weatherservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tenera.weatherservice.dto.HistoryReportDto;
import com.tenera.weatherservice.dto.WeatherReportDto;
import com.tenera.weatherservice.exception.ExceptionControllerAdvice;
import com.tenera.weatherservice.exception.OpenWeatherApiException;
import com.tenera.weatherservice.service.WeatherAppService;

/**
 *  Unit test class for WeatherAppController class
 * @author Satya Kolipaka
 *
 */
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class WeatherAppControllerTest {

	private MockMvc mockMvc;

	@Mock
	private WeatherAppService weatherAppServiceMock;

	@BeforeEach
	public void setup() {
		WeatherAppController weatherAppController = new WeatherAppController();
		ReflectionTestUtils.setField(weatherAppController, "weatherAppService", weatherAppServiceMock);
		this.mockMvc = MockMvcBuilders.standaloneSetup(weatherAppController)
				.setControllerAdvice(new ExceptionControllerAdvice()).build();
	}

	@Test
	void testCurrentWeatherReportSuccess() throws Exception {
		WeatherReportDto weatherReportDto = buildWeatherReportDto();
		when(weatherAppServiceMock.fetchWeatherReport("Frankfurt")).thenReturn(weatherReportDto);

		this.mockMvc.perform(get("/current").param("city", "Frankfurt")).andExpect(status().isOk())
				.andExpect(jsonPath("$.temp").value("-0.4"));
	}

	@Test
	void testCurrentWeatherReportError() throws Exception {
		this.mockMvc.perform(get("/current")).andExpect(status().isBadRequest());
	}

	@Test
	void testHistoryWeatherReportSuccess() throws Exception {

		when(weatherAppServiceMock.historyWeatherReport("Frankfurt")).thenReturn(buildHistoryReportDto());

		this.mockMvc.perform(get("/history").param("city", "Frankfurt")).andExpect(status().isOk())
				.andExpect(jsonPath("$.avgTemp").value("-2.57"));
	}

	@Test
	void testHistoryWeatherReportError() throws Exception {

		when(weatherAppServiceMock.historyWeatherReport("sjhds"))
				.thenThrow(new OpenWeatherApiException("city not found", 404));

		this.mockMvc.perform(get("/history").param("city", "sjhds")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.description").value("city not found"));
	}

	private WeatherReportDto buildWeatherReportDto() {
		return new WeatherReportDto(-0.4, 997.0, true);
	}

	private HistoryReportDto buildHistoryReportDto() {

		HistoryReportDto historyReportDto = new HistoryReportDto();
		List<WeatherReportDto> weatherReportDtoList = new ArrayList<>();
		weatherReportDtoList.add(new WeatherReportDto(-0.29, 998.0, true));
		weatherReportDtoList.add(new WeatherReportDto(-1.27, 999.0, false));
		weatherReportDtoList.add(new WeatherReportDto(-2.92, 999.0, false));
		weatherReportDtoList.add(new WeatherReportDto(-3.88, 999.0, false));
		weatherReportDtoList.add(new WeatherReportDto(-4.49, 999.0, false));

		historyReportDto.setAvgPressure(998.8);
		historyReportDto.setAvgTemp(-2.57);
		historyReportDto.setHistory(weatherReportDtoList);

		return historyReportDto;
	}
}
