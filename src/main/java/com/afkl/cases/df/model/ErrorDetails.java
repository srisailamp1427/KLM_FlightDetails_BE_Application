package com.afkl.cases.df.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * This is the model class for Error
 * 
 * @author srisailam
 */

@Data
@Getter
@Setter
public class ErrorDetails {
	private String errorCode;
	private String errorMessage;
}
