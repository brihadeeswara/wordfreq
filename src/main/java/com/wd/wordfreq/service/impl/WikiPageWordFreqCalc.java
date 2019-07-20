package com.wd.wordfreq.service.impl;

import static com.wd.wordfreq.constants.AppConstants.QUERY_PARAM_PAGEIDS;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.wd.wordfreq.bean.WordFreqRequest;
import com.wd.wordfreq.bean.wiki.WikiPageWordFreqRequest;
import com.wd.wordfreq.bean.wiki.WikiPageWordFreqResponse;
import com.wd.wordfreq.exception.WordFreqException;
import com.wd.wordfreq.service.AbstractWordFrequencyCalculator;
import com.wd.wordfreq.service.WordFreqCalc;

import lombok.extern.slf4j.Slf4j;
/**
 * Implementation of {@link WordFreqCalc} which reads
 * text from wiki page and calculates top N words
 * @author RakeshKShukla
 *
 */
@Service
@Slf4j
public class WikiPageWordFreqCalc extends AbstractWordFrequencyCalculator 
{
	private String wikiServerUrl;
	private String jsonPathForExtract;
	private String jsonPathForTitle;
	private WikiRestTemplate wikiRestTemplate;
	
	 public WikiPageWordFreqCalc(
	            @Value("${wiki.server.baseurl:https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json}") String wikiServerUrl,
	            @Value("${extract.json.path:query.pages.pageid.extract}") String jsonPathForExtract,
	            @Value("${title.json.path:query.pages.pageid.title}") String jsonPathForTitle,
	            WikiRestTemplate wikiRestTemplate) {
	        this.wikiServerUrl = wikiServerUrl;
	        this.jsonPathForExtract = jsonPathForExtract;
	        this.jsonPathForTitle = jsonPathForTitle;
	        this.wikiRestTemplate = wikiRestTemplate;
	    }
	 
	@Override
	public WikiPageWordFreqResponse getMostFrequentWords(WordFreqRequest request) 
	{
		if( ! (request instanceof WikiPageWordFreqRequest))
		{
			log.error("Invalid argument passed,{}", request);
			throw new IllegalArgumentException();
		}
		else
		{
			WikiPageWordFreqRequest wordRequest = (WikiPageWordFreqRequest) request;
			log.debug("WikiPageWordFreqRequest for wiki:{}",wordRequest.toString());
			wordStore.setMaxWords(wordRequest.getMaxTopWords());
			URI uri = getPageUri(wordRequest.getPageId());
			String result = wikiRestTemplate.getForObject(uri, String.class);
			log.debug("Json Response from wiki:{}", result);
			String pageId = String.valueOf(wordRequest.getPageId());
			String jsonQueryPathForTitle = jsonPathForTitle.replaceAll("pageid", pageId);
			String jsonQueryPathForExtract = jsonPathForExtract.replaceAll("pageid", pageId);
			try
			{
				String title = JsonPath.read(result, jsonQueryPathForTitle);
				String extract = JsonPath.read(result, jsonQueryPathForExtract);
				log.debug("Title of page from wiki:{}", title);
				log.debug("Extract of page from wiki:{}", extract);
				Map<Integer, List<String>> wordFreqMap = calculateFrequency(extract,wordRequest);
				log.debug("Calculated top words from wiki:{}", wordFreqMap);
				return WikiPageWordFreqResponse.builder().title(title).url(uri.toString()).topWords(wordFreqMap).build();
			}
			catch(PathNotFoundException e)
			{
				log.error("Json from wiki:{}", result);
				throw new WordFreqException("extract or title is missing in JSON response from wiki:"+ e.getMessage());
			}
		}
	}
	/**
	 * Returns uri for passed pageid
	 * @param pageId pageid
	 * @return URI
	 */
	private URI getPageUri(int pageId)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(wikiServerUrl).queryParam(QUERY_PARAM_PAGEIDS, pageId);
		return builder.build().toUri();
	}
}
