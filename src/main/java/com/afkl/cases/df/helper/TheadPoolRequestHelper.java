package com.afkl.cases.df.helper;

import java.util.concurrent.Callable;

import org.springframework.web.client.RestTemplate;

/**
 * This class for create the thread pool Request
 * 
 * @author srisailam
 */
public class TheadPoolRequestHelper<T> {

	/**
	 * This method will create the Callable object from RestTemplate 
	 * @param restTemplate
	 * @param url
	 * @param typeClass
	 * @return Callable<T>
	 */
	public Callable<T> createPoolRequest(RestTemplate restTemplate, String url, Class<T> typeClass) {
		return () -> restTemplate.getForObject(url, typeClass);
	}
}
