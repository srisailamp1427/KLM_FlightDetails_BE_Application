package com.afkl.cases.df.dto;

import com.afkl.cases.df.model.FareDetails;
import com.afkl.cases.df.model.LocationDetails;

import lombok.Getter;
import lombok.Setter;;

/**
 * This is the DTO class for Fares
 * 
 * @author srisailam
 */

@Getter
@Setter
public class FareResultDTO {
	private LocationDetails origin;
	private LocationDetails destination;
	private FareDetails fare;

}