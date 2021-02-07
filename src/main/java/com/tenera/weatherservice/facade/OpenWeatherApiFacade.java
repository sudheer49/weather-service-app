package com.tenera.weatherservice.facade;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.tenera.weatherservice.dto.WeatherReportDto;
import com.tenera.weatherservice.exception.OpenWeatherApiException;
import com.tenera.weatherservice.util.Constants;

/**
 * Facade class which contains the various functions to make a call to Open
 * weather API.
 * 
 * @author Satya_Kolipaka
 *
 */
@Service
public class OpenWeatherApiFacade {


	@Value("${config.openweather.apiUrl}")
	private String weatherApiUrl;

	@Value("${config.openweather.apiKey}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Method to retrieve the weather report from open weather API
	 * 
	 * @param location
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WeatherReportDto retrieveWeatherReport(String location) {

		final String url = weatherApiUrl + "weather";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("q", location)
				.queryParam("APPID", apiKey).queryParam("units", Constants.UNITS);
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(builder.build(true).toUri(), HttpMethod.GET, null, String.class);
			Map<String, Object> responseMap = new Gson().fromJson(response.getBody(), Map.class);

			return convertJsonObjectToWeatherReportDto(responseMap);

		} catch (HttpClientErrorException ex) {
			throw new OpenWeatherApiException(ex.getMessage(), ex.getRawStatusCode());
		} catch (Exception ex) {
			throw new OpenWeatherApiException(ex.getMessage(), 500);
		}
	}

	/**
	 * Method to retrieve last five weather reports from open weather API
	 * 
	 * @param city
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherReportDto> retrieveHistoryWeatherReport(String city) {

		final String url = weatherApiUrl + "forecast";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("q", city)
				.queryParam("APPID", apiKey).queryParam("cnt", 5).queryParam("units", Constants.UNITS);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(builder.build(true).toUri(), HttpMethod.GET, null, String.class);
			Map<String, Object> responseMap = new Gson().fromJson(response.getBody(), Map.class);

			List<Map<String, Object>> lastFiveDaysList = (List<Map<String, Object>>) responseMap.get("list");

			return lastFiveDaysList.stream().map(obj -> convertJsonObjectToWeatherReportDto(obj))
					.collect(Collectors.toList());

		} catch (HttpClientErrorException ex) {
			throw new OpenWeatherApiException(ex.getMessage(), ex.getRawStatusCode());
		} catch (Exception ex) {
			throw new OpenWeatherApiException(ex.getMessage(), 500);
		}
	}

	/**
	 * Method to convert json object to WeatherReportDto class
	 * @param jsonMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private WeatherReportDto convertJsonObjectToWeatherReportDto(Map<String, Object> jsonMap) {

		WeatherReportDto weatherReportDto = new WeatherReportDto();

		List<Object> weather = (List<Object>) jsonMap.get(Constants.WEATHER);
		Map<String, String> weatherMap = (Map<String, String>) weather.get(0);
		weatherReportDto.setUmbrella(determineUmbrellaRequired(weatherMap.get(Constants.MAIN)));

		Map<String, Object> mainMap = (Map<String, Object>) jsonMap.get(Constants.MAIN);
		weatherReportDto.setTemp((double) mainMap.get(Constants.TEMP));
		weatherReportDto.setPressure((double) mainMap.get(Constants.PRESSURE));

		return weatherReportDto;
	}

	/**
	 * Method to determine Umbrella is Required or not
	 * 
	 * @param weather
	 * @return
	 */
	private boolean determineUmbrellaRequired(String weather) {
		return weather.matches("Thunderstorm|Drizzle|Rain");
	}

}
