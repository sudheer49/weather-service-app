package com.tenera.weatherservice.dto;

/**
 * Class that holds error details 
 * 
 * @author Satya Kolipaka
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDto {

	private int status;
	private String title;
	private String description;
}
