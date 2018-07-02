package com.afkl.cases.df.aop;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.afkl.cases.df.model.MetricCacheDetails;

/**
 * This is the Aspect class to get the restful api metrics
 * 
 * @author srisailam
 */
@Aspect
@Component
public class AirportApiTimeAspect {
	private static final Logger logger = LogManager.getLogger(AirportApiTimeAspect.class);

	@Autowired
	private MetricCacheDetails metricDetails;

	@Around("execution(* com.afkl.cases.df.restcontroller.AirportDetailsController.*(..))||execution(* com.afkl.cases.df.restcontroller.AirportFaresController.*(..))")
	public Object metricDetails(ProceedingJoinPoint jointPoints) throws Throwable {

		long startTime = System.currentTimeMillis();
		Object details = jointPoints.proceed();
		ResponseEntity<Object> response = (ResponseEntity<Object>) details;

	    long durationTime = System.currentTimeMillis() - startTime;
		logger.info("total time for response" + durationTime);
		
		if (metricDetails.getStatusCodeMap().containsKey(response.getStatusCode().value())) {
			int code = metricDetails.getStatusCodeMap().get(response.getStatusCode().value());
			code++;
			metricDetails.getStatusCodeMap().put(response.getStatusCode().value(), code);

		} else {
			metricDetails.getStatusCodeMap().put(response.getStatusCode().value(), 1);
		}

	

		metricDetails.getResponseTimeList().add(durationTime);

		return details;
	}

}