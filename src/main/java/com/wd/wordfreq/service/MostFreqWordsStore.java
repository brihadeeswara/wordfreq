package com.wd.wordfreq.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import lombok.Setter;
/**
 * Stores most frequent words and their count
 * @author RakeshKShukla
 *
 */
public class MostFreqWordsStore 
{
	@Setter
	private int maxWords;
	/**
	 * A NavigableMap
	 * {key} - count of word
	 * {value} - list of words
	 */
	private NavigableMap<Integer, List<String>> wordCountMap = new TreeMap<>(Comparator.reverseOrder());
	/**
	 * Returns Map containing count and list of words
	 * @return wordCountMap any change in return map will get reflected in this store
	 */
	public Map<Integer, List<String>> getMap()
	{
		return wordCountMap;
	}
	/**
	 * Adds word and its count to store
	 * @param count number of occurrences
	 * @param word 
	 */
	public void addWordWithCountToStore( int count, String word)
	{
		List<String> wordList = wordCountMap.computeIfAbsent(count,ArrayList::new);
		wordList.add(word);
		// lets remove words with small counts so that we don't have big Map
		// this will make TreeMap's O(logN) algos to O(1) 
		if( wordCountMap.size() > maxWords )
		{
			wordCountMap.remove(wordCountMap.lastKey());
		}
	}
}
