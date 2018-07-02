package com.afkl.cases.df.service;

import org.springframework.http.ResponseEntity;


/**
 * This interface give the airport metric information.
 * 
 * @author srisailam
 */
public interface AirportMetricService {
	ResponseEntity<Object> getAirportMetrics();
}
