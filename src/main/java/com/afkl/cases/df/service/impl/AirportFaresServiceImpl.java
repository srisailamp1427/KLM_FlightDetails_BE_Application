package com.afkl.cases.df.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.afkl.cases.df.dto.FareResultDTO;
import com.afkl.cases.df.helper.AppConstants;
import com.afkl.cases.df.helper.ResponseHelper;
import com.afkl.cases.df.helper.TheadPoolRequestHelper;
import com.afkl.cases.df.model.FareDetails;
import com.afkl.cases.df.model.LocationDetails;
import com.afkl.cases.df.oauthclient.OAuthClientConstants;
import com.afkl.cases.df.restcontroller.AirportFaresController;
import com.afkl.cases.df.service.AirportFaresService;

/**
 * This service class for implementation of the airport Fares.
 * 
 * @author srisailam
 */
@Service
public class AirportFaresServiceImpl implements AirportFaresService {
	private static final Logger logger = LogManager.getLogger(AirportFaresController.class);
	@Value("${base_url}")
	private String baseUrl;

	@Value("${threadpool.noofthreads}")
	private Integer noOfThread;

	@Override
	public ResponseEntity<Object> getFareDetails(RestTemplate restTemplate, String token, String origin,
			String destination) throws Exception {

		if (token != null) {
			ExecutorService threadPool = Executors.newFixedThreadPool(noOfThread);

			Callable<LocationDetails> originDetailRequest = new TheadPoolRequestHelper<LocationDetails>()
					.createPoolRequest(restTemplate, getLocationInfoDetailsURL(token, origin), LocationDetails.class);
			Callable<LocationDetails> destinationDetailRequest = new TheadPoolRequestHelper<LocationDetails>()
					.createPoolRequest(restTemplate, getLocationInfoDetailsURL(token, destination),
							LocationDetails.class);
			Callable<FareDetails> fareDetailRequest = new TheadPoolRequestHelper<FareDetails>().createPoolRequest(
					restTemplate, getFareControllerURL(token, origin, destination), FareDetails.class);

			Future<LocationDetails> originTask = threadPool.submit(originDetailRequest);
			Future<LocationDetails> destTask = threadPool.submit(destinationDetailRequest);
			Future<FareDetails> fareTask = threadPool.submit(fareDetailRequest);

			// Creating FareResultDTO Object
			ResponseEntity<Object> faresData = getFareDetailsResults(originTask, destTask, fareTask);

			threadPool.shutdown();

			return new ResponseEntity<>(faresData, HttpStatus.OK);
		}

		return ResponseHelper.getErorReposneEntrity(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * This method for generating faresDetails
	 */
	private ResponseEntity<Object> getFareDetailsResults(Future<LocationDetails> origin, Future<LocationDetails> dest,
			Future<FareDetails> fare) {

		FareResultDTO fares = null;
		try {
			fares = new FareResultDTO();
			fares.setOrigin(origin.get());
			fares.setDestination(dest.get());
			fares.setFare(fare.get());
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
			return ResponseHelper.getErorReposneEntrity(HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<>(fares, HttpStatus.OK);
	}

	/**
	 * This method for get the Fare Details URL
	 */
	private String getFareControllerURL(String token, String originCode, String destinationCode) {

		return new StringBuilder().append(baseUrl).append(AppConstants.FARES).append(AppConstants.SLASH)
				.append(originCode).append(AppConstants.SLASH).append(destinationCode).append(AppConstants.QMARK)
				.append(OAuthClientConstants.ACCESS_TOKEN).append(AppConstants.EQUAL).append(token).toString();
	}

	/**
	 * This method for get the locationDetails url
	 */
	private String getLocationInfoDetailsURL(String token, String locationCode) {

		return new StringBuilder().append(baseUrl).append(AppConstants.AIRPORTS).append(AppConstants.SLASH)
				.append(locationCode).append(AppConstants.QMARK).append(OAuthClientConstants.ACCESS_TOKEN)
				.append(AppConstants.EQUAL).append(token).toString();

	}

}
