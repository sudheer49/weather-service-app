package com.tenera.weatherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenera.weatherservice.dto.ErrorDetailsDto;
import com.tenera.weatherservice.dto.WeatherReportDto;
import com.tenera.weatherservice.service.WeatherAppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

	
	@ApiOperation(value = "Display current weather report on specified city", response = WeatherReportDto.class, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully displayed weather report", response = WeatherReportDto.class),
			@ApiResponse(code = 500, message = "Unexpected Internal Error", response = ErrorDetailsDto.class) })
	@GetMapping("/current")
	public ResponseEntity<WeatherReportDto> currentWeatherReport(@RequestParam(required = true) String city) {
		return new ResponseEntity<>(weatherAppService.fetchWeatherReport(city),HttpStatus.OK);
	}
}
