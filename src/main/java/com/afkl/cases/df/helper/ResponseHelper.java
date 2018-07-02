package com.afkl.cases.df.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.afkl.cases.df.model.ErrorDetails;

/**
 * This helper class for the  response 
 * 
 * @author srisailam
 */

public class ResponseHelper {

	/**
	 * This method return ErrorDetails pojo based code, description
	 * @param code
	 * @param description
	 * @return ErrorDetails
	 */
	public static ErrorDetails getErrorDetails(String code, String description) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(code);
		errorDetails.setErrorMessage(description);
		return errorDetails;
	}

	
	/**
	 * This method return Response Entity object for Error based on input code
	 * @param code
	 * @return ResponseEntity<Object>
	 */
	public static ResponseEntity<Object> getErorReposneEntrity(HttpStatus code) {
		return new ResponseEntity<>(getErrorDetails(code.toString(), code.name()), code);
	}

}
