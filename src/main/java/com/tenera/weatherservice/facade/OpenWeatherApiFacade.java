package com.tenera.weatherservice.facade;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.tenera.weatherservice.dto.WeatherReportDto;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherApiFacade.class);

	@Value("${config.openweather.apiUrl}")
	private String weatherApiUrl;

	@Value("${config.openweather.apiKey}")
	private String apiKey;

	private RestTemplate restTemplate = new RestTemplate();

	/**
	 * Method to retrieve the weather report from open weather API
	 * 
	 * @param location
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WeatherReportDto retrieveWeatherReport(String location) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weatherApiUrl).queryParam("q", location)
				.queryParam("APPID", apiKey).queryParam("units", Constants.UNITS);
		ResponseEntity<String> response = null;
		WeatherReportDto weatherReportDto = new WeatherReportDto();
		try {
			response = restTemplate.exchange(builder.build(true).toUri(), HttpMethod.GET, null, String.class);
			Map<String, Object> responseMap = new Gson().fromJson(response.getBody(), Map.class);

			List<Object> weather = (List<Object>) responseMap.get(Constants.WEATHER);
			Map<String, String> weatherMap = (Map<String, String>) weather.get(0);
			weatherReportDto.setUmbrella(determineUmbrellaRequried(weatherMap.get(Constants.MAIN)));

			Map<String, Object> mainMap = (Map<String, Object>) responseMap.get(Constants.MAIN);
			weatherReportDto.setTemp((double) mainMap.get(Constants.TEMP));
			weatherReportDto.setPressure((double) mainMap.get(Constants.PRESSURE));

			return weatherReportDto;
		} catch (HttpClientErrorException ex) {
			LOGGER.error("Error with openweather API: {}", ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("There is some error : {}", ex.getMessage());
		}
		return null;

	}

	/**
	 * 
	 * @param weather
	 * @return
	 */
	private boolean determineUmbrellaRequried(String weather) {
		return weather.matches("Thunderstorm|Drizzle|Rain");
	}
}
