package com.afkl.cases.df.model;

import lombok.Getter;
import lombok.Setter;
/**
 * This Pojo class for  metric 
 * 
 * @author srisailam
 */


@Getter
@Setter
public class Metrics {
	private long minRespTime;
	private long maxRespTime;
	private double avgRespTimeAllReq;
	private int noOfResp200;
	private int noOfResp404;
	private int noOfResp500;
	private int totalNoOfReq;

}
