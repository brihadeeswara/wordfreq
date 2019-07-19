package com.wd.wordfreq.service.impl;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.apache.http.annotation.Experimental;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.wd.wordfreq.bean.wiki.WikiPageWordFreqRequest;
import com.wd.wordfreq.bean.wiki.WikiPageWordFreqResponse;
import com.wd.wordfreq.constants.AppConstants;
import com.wd.wordfreq.exception.WordFreqException;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class WikiPageWordFreqCalcTest {

	@Autowired
	private WikiRestTemplate wikiRestTemplate;
	private MockRestServiceServer mockServer;
	@Autowired
	private WikiPageWordFreqCalc svc;
	@Before
	public void setup() {
		mockServer = MockRestServiceServer.createServer(wikiRestTemplate);
	}
	@Test
	public void testGetMostFrequentWords() throws IOException {
		File file = new ClassPathResource("sampleresponse.json").getFile();
		String fileData = new String(Files.readAllBytes(file.toPath()));
		mockServer.expect(requestTo("http://localhost:8080?pageids=1"))
				.andRespond(withSuccess(fileData, MediaType.APPLICATION_JSON));
		WikiPageWordFreqResponse response = svc.getMostFrequentWords(WikiPageWordFreqRequest.builder().maxTopWords(5)
				.pageId(1).wordRegex(Optional.of(AppConstants.DEFAULT_WORDREGEX)).build());
		assertEquals("Sample", response.getTitle());
		assertEquals("http://localhost:8080?pageids=1", response.getUrl());
		assertEquals("questions", response.getTopWords().get(16).get(0));
		assertEquals("overflow", response.getTopWords().get(12).get(0));
		assertEquals("stack", response.getTopWords().get(12).get(1));
		assertEquals("that", response.getTopWords().get(11).get(0));
		assertEquals("users", response.getTopWords().get(10).get(0));
		assertEquals("answer", response.getTopWords().get(7).get(0));
		assertEquals("answers", response.getTopWords().get(7).get(1));
		assertEquals("reputation", response.getTopWords().get(7).get(2));
		mockServer.verify();
	}
	@Test(expected = WordFreqException.class)
	public void testGetMostFrequentWordsNotFound() throws IOException {
		mockServer.expect(requestTo("http://localhost:8080?pageids=1"))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));
		svc.getMostFrequentWords(WikiPageWordFreqRequest.builder().maxTopWords(5)
				.pageId(1).wordRegex(Optional.of(AppConstants.DEFAULT_WORDREGEX)).build());
		mockServer.verify();
	}
}
