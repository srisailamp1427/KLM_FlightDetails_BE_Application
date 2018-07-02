package com.afkl.cases.df.restcontroller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.afkl.cases.df.service.AirportFaresService;
import com.afkl.cases.df.service.impl.OAuthServiceImpl;

/**
 * This class gives the fare api details
 * 
 * @author srisailam
 */

@RestController
@RequestMapping("/fares/{origin}/{destination}")
public class AirportFaresController {
	@Autowired
	private OAuthServiceImpl oAuthServiceImpl;

	@Autowired
	private AirportFaresService airportFaresService;

	@Autowired
	private RestTemplate restTemplate;
	/**
	 * This method for get the fares details between origin and destination
	 * 
	 * @throws Exception
	 */
	@RequestMapping(method = GET)
	public ResponseEntity<Object> getFareDetails(@PathVariable("origin") String origin,
			@PathVariable("destination") String destination) throws Exception {
		// this is for get the token
		String token = oAuthServiceImpl.getOAuthClientToken();
		return airportFaresService.getFareDetails(restTemplate,token, origin, destination);
	}

}