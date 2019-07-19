package com.wd.wordfreq.bean;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Base class for word frequency response 
 * @author RakeshKShukla
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class WordFreqResponse 
{
	/**
	 * map containing top words
	 * {key}: number of occurrences of words
	 * {value}: list of words
	 */
	protected Map<Integer, List<String>> topWords;
}
