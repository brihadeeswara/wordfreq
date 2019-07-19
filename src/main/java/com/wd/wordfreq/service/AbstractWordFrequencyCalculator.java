package com.wd.wordfreq.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.wd.wordfreq.bean.WordFreqRequest;

/**
 * Calculates frequency of words and returns top N words It reads words from
 * passed text as string
 * 
 * @author RakeshKShukla
 *
 */
public abstract class AbstractWordFrequencyCalculator implements WordFreqCalc {

	/**
	 * reference to word store
	 */
	@Autowired
	protected MostFreqWordsStore wordStore;

	/**
	 * Returns map which contains Top N words by their frequency in passed text
	 * where N is obtained from passed {@link WordFreqRequest} 
	 * @param text String representing whole text 
	 * @param request WordFreqRequest
	 * @return Map of top word and their number of occurrences
	 */
	public Map<Integer, List<String>> calculateFrequency(String text, WordFreqRequest request) {
		Stream<String> wordStream = Stream.of(text.split("\\s+")).map(String::toLowerCase);
		if (request.getWordRegex().isPresent()) {
			wordStream = wordStream.filter(word -> word.matches(request.getWordRegex().get()));
		}
		// choosing O(NlogN) algo by sorting the list, we can also create HashMap 
		// of words themselves and store their occurrences, and then iterate over
		// map in second phase and return top N words. I have picked sorting one
		// as to me it is better to store only N element in Map
		List<String> sortedList = wordStream.sorted().collect(Collectors.toList());
		if (sortedList.size() > 0) {
			String prevWord = sortedList.get(0);
			int count = 0;
			for (String word : sortedList) {
				if (prevWord.equals(word)) {
					++count;
				} else {
					wordStore.addWordWithCountToStore(count, prevWord);
					count = 1;
					prevWord = word;
				}
			}
			// handle last word
			wordStore.addWordWithCountToStore(count, prevWord);
		}
		return wordStore.getMap();
	}

}
