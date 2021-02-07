package com.tenera.weatherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenera.weatherservice.dto.ErrorDetailsDto;
import com.tenera.weatherservice.dto.HistoryReportDto;
import com.tenera.weatherservice.dto.WeatherReportDto;
import com.tenera.weatherservice.service.WeatherAppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class which exposes APIs relates to Weather service app.
 * 
 * @author Satya Kolipaka
 *
 */
@Api
@RestController
public class WeatherAppController {

	@Autowired
	private WeatherAppService weatherAppService;

	/**
	 * End point to fetch current weather report based on given city
	 * 
	 * @param city
	 * @return
	 */
	@ApiOperation(value = "Display current weather report on specified city", response = WeatherReportDto.class, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully displayed weather report", response = WeatherReportDto.class),
			@ApiResponse(code = 404, message = "Error with external API", response = ErrorDetailsDto.class),
			@ApiResponse(code = 500, message = "Unexpected Internal Error", response = ErrorDetailsDto.class) })
	@GetMapping("/current")
	public ResponseEntity<WeatherReportDto> currentWeatherReport(
			@ApiParam(value = "city", required = true) @RequestParam(required = true) String city) {
		return new ResponseEntity<>(weatherAppService.fetchWeatherReport(city), HttpStatus.OK);
	}

	/**
	 * End point to fetch history of last 5 weather report
	 * 
	 * @param city
	 * @return
	 */
	@ApiOperation(value = "Display last 5 weather reports on specified city", response = WeatherReportDto.class, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully displayed last 5 weather reports", response = HistoryReportDto.class),
			@ApiResponse(code = 404, message = "Error with external API", response = ErrorDetailsDto.class),
			@ApiResponse(code = 500, message = "Unexpected Internal Error", response = ErrorDetailsDto.class) })
	@GetMapping("/history")
	public ResponseEntity<HistoryReportDto> historyWeatherReport(
			@ApiParam(value = "city", required = true) @RequestParam(required = true) String city) {
		return new ResponseEntity<>(weatherAppService.historyWeatherReport(city), HttpStatus.OK);
	}
}
