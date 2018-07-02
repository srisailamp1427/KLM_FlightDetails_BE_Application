package com.afkl.cases.df.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This is the model class for Fares
 * 
 * @author srisailam
 */

@Getter
@Setter
public class FareDetails {
	private Double amount;
	private Currency currency;
	private String origin;
	private String destination;
}