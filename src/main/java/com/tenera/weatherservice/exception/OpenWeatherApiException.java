package com.tenera.weatherservice.exception;

/**
 * This class is custom exception class which will be thrown if any error in
 * Open weather API
 * 
 * @author Satya Kolipaka
 *
 */
public class OpenWeatherApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final int statusCode;

	public OpenWeatherApiException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
