package com.wd.wordfreq.service;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.wd.wordfreq.exception.WordFreqException;

/**
 * Handles Http errors while using RestTemplates
 * @author RakeshKShukla
 */
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return httpResponse.getStatusCode().series() == Series.CLIENT_ERROR;
	}
	/**
	 * Causes simple string to be returned in place of stacktrace
	 *TODO should be changed to represent errors better 
	 */
	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		HttpStatus.Series series = httpResponse.getStatusCode().series();
		if (series == HttpStatus.Series.CLIENT_ERROR) {
			if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new WordFreqException("Http 404 received from server,check URL");
			} else {
				throw new WordFreqException(
						"Client Error while connecting to remote server, Error Code:" + httpResponse.getStatusText());
			}
		} 
	}
}