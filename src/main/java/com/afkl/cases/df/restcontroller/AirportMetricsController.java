package com.afkl.cases.df.restcontroller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.afkl.cases.df.service.AirportMetricService;

/**
 * This class gives the  api metrics details
 * 
 * @author srisailam
 */

@RestController
public class AirportMetricsController {

	@Autowired
	private AirportMetricService airportMetricService;

	/**
	 * This method for get metric details
	 * 
	 */
	@RequestMapping(value = "/metric", method = GET)
	public ResponseEntity<Object> getMetricDetails() {
		return airportMetricService.getAirportMetrics();
	}

}