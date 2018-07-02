package com.afkl.cases.df.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.afkl.cases.df.helper.AppConstants;
import com.afkl.cases.df.helper.ResponseHelper;
import com.afkl.cases.df.oauthclient.OAuthClientConstants;
import com.afkl.cases.df.restcontroller.AirportDetailsController;
import com.afkl.cases.df.service.AirportDetailsService;

/**
 * This service class for implementation of the airport Details.
 * 
 * @author srisailam
 */
@Service
public class AirportDetailsServiceImpl implements AirportDetailsService {
	private static final Logger logger = LogManager.getLogger(AirportDetailsController.class);
	@Value("${base_url}")
	private String baseUrl;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public ResponseEntity<Object> getListOfAirports(String token) {
		try {
			logger.info("OAuthToken..." + token);
			
			// this is for get the token
			if (token != null) {
				String airportListUrl = new StringBuilder().append(baseUrl).append(AppConstants.SLASH)
						.append(AppConstants.AIRPORTS).append(AppConstants.QMARK)
						.append(OAuthClientConstants.ACCESS_TOKEN).append(AppConstants.EQUAL).append(token).toString();

				Object airports = restTemplate.getForObject(airportListUrl, Object.class);
				return new ResponseEntity<>(airports, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Internal Error Occured.." + e.getMessage());
			return ResponseHelper.getErorReposneEntrity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseHelper.getErorReposneEntrity(HttpStatus.UNAUTHORIZED);
	}

	@Override
	public ResponseEntity<Object> getAirportsListParams(String token, String size, String page, String lang,
			String term) {
		try {

			logger.info("oAuthClientToken..." + token);

			if (token != null) {
				String airportListUrl = new StringBuilder().append(baseUrl).append(AppConstants.AIRPORTS)
						.append(AppConstants.QMARK).append(OAuthClientConstants.ACCESS_TOKEN).append(AppConstants.EQUAL)
						.append(token).append(AppConstants.AMPERSAND).append(AppConstants.SIZE)
						.append(AppConstants.EQUAL).append(size).append(AppConstants.AMPERSAND)
						.append(AppConstants.PAGE).append(AppConstants.EQUAL).append(page)
						.append(AppConstants.AMPERSAND).append(AppConstants.LANG).append(AppConstants.EQUAL)
						.append(lang).append(AppConstants.AMPERSAND).append(AppConstants.TERM)
						.append(AppConstants.EQUAL).append(term).toString();

				Object airports = restTemplate.getForObject(airportListUrl, Object.class);
				return new ResponseEntity<>(airports, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Internal Error Occured.." + e.getMessage());
			return ResponseHelper.getErorReposneEntrity(HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return ResponseHelper.getErorReposneEntrity(HttpStatus.UNAUTHORIZED);
	}

	@Override
	public ResponseEntity<Object> getAirportsCodesDetails(String token, String code, String lang) {
		try {

			logger.info("token" + token);
			if (token != null) {

				String codeUrl = new StringBuilder().append(baseUrl).append(AppConstants.AIRPORTS)
						.append(AppConstants.QMARK).append(AppConstants.TERM).append(AppConstants.EQUAL).append(code)
						.append(AppConstants.AMPERSAND).append(OAuthClientConstants.ACCESS_TOKEN)
						.append(AppConstants.EQUAL).append(token).append(AppConstants.AMPERSAND)
						.append(AppConstants.LANG).append(AppConstants.EQUAL).append(lang).toString();

				Object airports = restTemplate.getForObject(codeUrl, Object.class);

				return new ResponseEntity<>(airports, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Internal Error Occured.." + e.getMessage());
			return ResponseHelper.getErorReposneEntrity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseHelper.getErorReposneEntrity(HttpStatus.UNAUTHORIZED);

	}

}
