package com.wd.wordfreq.logger;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
/**
 * Utility class which intercepts http request/response and
 * logs time taken to response from http server 
 * @author RakeshKShukla
 *
 */
@Slf4j
public class HttpLoggerInterceptor implements ClientHttpRequestInterceptor
{
	/**
	 * Logs request url, time taken in  getting response and http response code
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		StopWatch timer = new StopWatch();
		timer.start();
		ClientHttpResponse response = execution.execute(request, body);
		timer.stop();
		log.info("REQUEST URL:{}, Time:{}, HttpRespCode:{}", request.getURI(), timer.getLastTaskTimeMillis(),response.getStatusCode());
		return response; 
	}
}
