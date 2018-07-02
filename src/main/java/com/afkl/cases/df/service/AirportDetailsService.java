package com.afkl.cases.df.service;

import org.springframework.http.ResponseEntity;
/**
 * This interface give the airport details information.
 * 
 * @author srisailam
 */
public interface AirportDetailsService {
	 ResponseEntity<Object>  getListOfAirports(String token);
	 ResponseEntity<Object> getAirportsListParams(String token,String size,String page, String lang, String term);
	 ResponseEntity<Object> getAirportsCodesDetails(String token,String code, String lang);
}
