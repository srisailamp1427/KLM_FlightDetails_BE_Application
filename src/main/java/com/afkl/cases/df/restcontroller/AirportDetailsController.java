package com.afkl.cases.df.restcontroller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afkl.cases.df.service.AirportDetailsService;
import com.afkl.cases.df.service.impl.OAuthServiceImpl;

/**
 * This classs give the api details of airport list.
 * 
 * @author srisailam
 */

@RestController
public class AirportDetailsController {

	@Autowired
	private OAuthServiceImpl oAuthServiceImpl;

	@Autowired
	private AirportDetailsService airportDetailsService;

	/**
	 * This method for get the airport list details based on pararms
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/airports", method = GET)
	public ResponseEntity<Object> getAirportsListParams(@RequestParam String size, @RequestParam String page,
			@RequestParam String lang, @RequestParam String term) throws Exception {
		// this is for get the token
		String token = oAuthServiceImpl.getOAuthClientToken();
		return airportDetailsService.getAirportsListParams(token, size, page, lang, term);
	}

	/**
	 * This method for get the airport list details with out pararms
	 */
	@RequestMapping(value = "/airportlist", method = GET)
	public ResponseEntity<Object> getListOfAirports() throws Exception {
		// this is for get the token
		String token = oAuthServiceImpl.getOAuthClientToken();
		return airportDetailsService.getListOfAirports(token);
	}

	/**
	 * This method for get the Airport Details based on code , lang
	 */

	@RequestMapping(value = "/airport/{code}", method = GET)
	public ResponseEntity<Object> getAirportsCodesDetails(@PathVariable("code") String code, @RequestParam String lang)
			throws Exception {
		// this is for get the token
		String token = oAuthServiceImpl.getOAuthClientToken();
		return airportDetailsService.getAirportsCodesDetails(token, code, lang);
	}

}
