package com.afkl.cases.df.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This class stores metric details
 * 
 * @author srisailam
 */

@Scope(value = "singleton")
@Component
public class MetricCacheDetails {
	Map<Integer, Integer> statusCodesMap = new HashMap<>();
	List<Long> response = new ArrayList<>();

	@Cacheable("map")
	public Map<Integer, Integer> getStatusCodeMap() {

		return statusCodesMap;
	}

	@Cacheable("list")
	public List<Long> getResponseTimeList() {

		return response;
	}

}
