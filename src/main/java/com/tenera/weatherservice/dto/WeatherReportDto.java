package com.tenera.weatherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class holds weather report information
 * 
 * @author Satya_Kolipaka
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherReportDto {

	private double temp;
	private double pressure;
	private boolean umbrella;
}
