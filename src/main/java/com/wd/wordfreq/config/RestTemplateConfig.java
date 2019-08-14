package com.wd.wordfreq.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Configures RestTemplate , it adds http connection pool manager by default
 * also configures other connection parameters
 * 
 * @author RakeshKShukla
 *
 */
@Slf4j
@Configuration
public class RestTemplateConfig {
	/**
	 * max connection in pool, default one is enough for current command line uses
	 */
	@Value("${wiki.http.conn.pool.max:1}")
	private int maxConnInPool;
	/**
	 * idle timeout will be used to remove idle connections from pool
	 */
	@Value("${wiki.http.conn.pool.idle.timeout:30000}")
	private int idleTimeOutInMillis;
	/**
	 * so_connect timeout
	 */
	@Value("${wiki.http.conn.pool.connect.timeout:10000}")
	private int connectTimeout;
	/**
	 * so_read timeout, default is 20sec
	 */
	@Value("${wiki.http.conn.pool.read.timeout:20000}")
	private int readTimeout;

	/**
	 * Configures Http pooled connection manager with daemon thread to remove idle
	 * connections This method is not that useful in single command line use of this
	 * app
	 * 
	 * @return HttpClientConnectionManager pooled connection manager
	 */
	@Bean
	public HttpClientConnectionManager connectionManager() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxConnInPool);
		connectionManager.setDefaultMaxPerRoute(maxConnInPool);
		ExecutorService closeIdleConnExecutor = Executors.newSingleThreadExecutor(runnable -> {
			Thread removeIdleConnThread = new Thread(runnable);
			removeIdleConnThread.setDaemon(true);
			return removeIdleConnThread;
		});
		closeIdleConnExecutor.execute(() -> {
			while (true) {
				log.debug("cleaning up expired and idle connections");
				connectionManager.closeExpiredConnections();
				connectionManager.closeIdleConnections(idleTimeOutInMillis, TimeUnit.SECONDS);
				log.debug("cleaned up expired and idle connections");
				try {
					Thread.sleep(idleTimeOutInMillis - 1000l);
				} catch (InterruptedException e) {
					log.error("Thread got interrupted while sleeping,exiting loop");
					continue;
				}
			}
		});
		return connectionManager;
	}

	/**
	 * Add default timeout for connections if there is no keep-alive header from 
	 * server
	 * @param connectionManager pooled connection manager
	 * @return HttpClient http client
	 */
	@Bean
	public HttpClient httpClient(HttpClientConnectionManager connectionManager) {
		return HttpClients.custom().setConnectionManager(connectionManager)
				.setKeepAliveStrategy((response, context) -> {
					HeaderElementIterator it = new BasicHeaderElementIterator(
							response.headerIterator(HTTP.CONN_KEEP_ALIVE));
					while (it.hasNext()) {
						HeaderElement he = it.nextElement();
						String param = he.getName();
						String value = he.getValue();
						if (value != null && param.equalsIgnoreCase("timeout")) {
							return Long.parseLong(value) * 1000;
						}
					}
					return idleTimeOutInMillis;
				}
				).build();
	}
	/**
	 * Returns Http client factory with connect,read timeouts and with passed http client
	 * @param httpClient configured http clients
	 * @return ClientHttpRequestFactory
	 */
	@Bean
	public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		requestFactory.setConnectTimeout(connectTimeout);
		requestFactory.setReadTimeout(readTimeout);
		return requestFactory;
	}
}
