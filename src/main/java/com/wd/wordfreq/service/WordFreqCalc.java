package com.wd.wordfreq.service;

import com.wd.wordfreq.bean.WordFreqRequest;
import com.wd.wordfreq.bean.WordFreqResponse;

/**
 * Base interface for this application
 * @author RakeshKShukla
 *
 */
@FunctionalInterface
public interface WordFreqCalc 
{
	/**
	 * Returns top N words in form of WordFreqResponse corresponding to 
	 * passed WordFreqRequest
	 * @param request WordFreqRequest object containing required parameter to fetch text
	 * @return WordFreqResponse
	 */
	WordFreqResponse getMostFrequentWords(WordFreqRequest request);
}
