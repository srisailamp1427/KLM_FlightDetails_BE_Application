package com.afkl.cases.df.service.impl;

import java.util.LongSummaryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.afkl.cases.df.model.Metrics;
import com.afkl.cases.df.model.MetricCacheDetails;
import com.afkl.cases.df.service.AirportMetricService;

/**
 * This service class for implementation of the airport metrics.
 * 
 * @author srisailam
 */
@Service
public class AirportMetricsServiceImpl implements AirportMetricService {

	@Autowired
	private MetricCacheDetails metricCaacheDetails;

	@Override
	public ResponseEntity<Object> getAirportMetrics() {

		Metrics metrics = new Metrics();

		if (!metricCaacheDetails.getStatusCodeMap().isEmpty()) {
			metrics.setNoOfResp200(getStatusCodeCount(200));
			metrics.setNoOfResp404(getStatusCodeCount(404));
			metrics.setNoOfResp500(getStatusCodeCount(500));
			metrics.setTotalNoOfReq(getTotalNoOfRequest(metrics));
		}

		if (!CollectionUtils.isEmpty(metricCaacheDetails.getResponseTimeList())
				&& metricCaacheDetails.getResponseTimeList().size() > 0) {

			LongSummaryStatistics stats = metricCaacheDetails.getResponseTimeList().stream().mapToLong((x) -> x)
					.summaryStatistics();
			metrics.setAvgRespTimeAllReq(stats.getAverage());
			metrics.setMinRespTime(stats.getMin());
			metrics.setMaxRespTime(stats.getMax());
		}
		return new ResponseEntity<>(metrics, HttpStatus.OK);
	}

	/**
	 * This method for get the StatusCode Count
	 * 
	 */
	private int getStatusCodeCount(int code) {
		int count = 0;
		if (metricCaacheDetails.getStatusCodeMap().containsKey(code))
			count = metricCaacheDetails.getStatusCodeMap().get(code);
		return count;
	}

	/**
	 * This method for get the TotalNoOfRequest
	 *
	 */
	private int getTotalNoOfRequest(Metrics metrics) {
		return metrics.getNoOfResp200() + metrics.getNoOfResp404() + metrics.getNoOfResp500();
	}

}
