package com.afkl.cases.df.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * This is the model class for Location
 * 
 * @author srisailam
 */
@Getter
@Setter
public class LocationDetails {
	private String code;
	private String name;
	private String description;
	private Coordinates coordinates;
	private LocationDetails parent;
	private Set<LocationDetails> children;
}