package com.wd.wordfreq.service.impl;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.wd.wordfreq.logger.HttpLoggerInterceptor;
import com.wd.wordfreq.service.RestTemplateResponseErrorHandler;
/**
 * RestTemplate used for connecting to wiki
 * @author RakeshKShukla
 *
 */
@Component("wikiRestTemplate")
public class WikiRestTemplate extends RestTemplate
{
	@Autowired
	private ClientHttpRequestFactory httpRequestFactory;
	/**
	 * Sets HttpLoggerInterceptor,RestTemplateResponseErrorHandler and
	 * configured httpRequestFactory in this instance
	 */
	@PostConstruct
	public void init()
	{
		setRequestFactory(httpRequestFactory);
		setInterceptors(Arrays.asList(new HttpLoggerInterceptor()));
		setErrorHandler(new RestTemplateResponseErrorHandler());
	}
}
