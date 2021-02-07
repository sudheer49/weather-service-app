package com.tenera.weatherservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class holds history weather report
 * 
 * @author Satya Kolipaka
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryReportDto {
	private double avgTemp;
	private double avgPressure;
	private List<WeatherReportDto> history;
}
