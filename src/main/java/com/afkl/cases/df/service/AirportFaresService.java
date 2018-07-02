package com.afkl.cases.df.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
/**
 * This interface give the airport fares information.
 * 
 * @author srisailam
 */
public interface AirportFaresService {
	ResponseEntity<Object> getFareDetails(RestTemplate restTemplate,String token, String origin,String destination) throws Exception;
}
