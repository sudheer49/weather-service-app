package com.tenera.weatherservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tenera.weatherservice.dto.ErrorDetailsDto;

/**
 * This class in a controller advise that is executed when the custom exception
 * is thrown in controller classes. It also helps to convert the exception into
 * and Errors to be returned with appropriate HTTP code in response
 * 
 * @author Satya Kolipaka
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	/**
	 * This method helps to build the exception details if any issue with request
	 * itself
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class })
	public ResponseEntity<ErrorDetailsDto> methodArgumentTypeMismatchException(final Exception exception) {
		ErrorDetailsDto errorDetail = new ErrorDetailsDto(HttpStatus.BAD_REQUEST.value(), "Bad Request",
				exception.getMessage());
		return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method help to build exception details if any error in external API fetching
	 * 
	 * @return
	 */
	@ExceptionHandler(OpenWeatherApiException.class)
	public ResponseEntity<ErrorDetailsDto> openWeatherApiException(final OpenWeatherApiException exception) {
		ErrorDetailsDto errorDetail = new ErrorDetailsDto(exception.getStatusCode(), "Error with external API",
				exception.getMessage());
		return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method helps to Errors to controller if there is any Global Exception.
	 * 
	 * @param exception
	 * 
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsDto> handleException(final Exception exception) {
		ErrorDetailsDto errorDetail = new ErrorDetailsDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server error", exception.getMessage());
		return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
