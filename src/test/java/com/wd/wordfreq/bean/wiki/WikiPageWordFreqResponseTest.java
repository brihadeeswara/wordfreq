package com.wd.wordfreq.bean.wiki;

import static com.wd.wordfreq.constants.AppConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.wd.wordfreq.bean.wiki.WikiPageWordFreqResponse;

public class WikiPageWordFreqResponseTest {

	@Test
	public void test() {
		Map<Integer, List<String>> wordCountMap = new TreeMap<Integer, List<String>>(Comparator.reverseOrder());
		wordCountMap.put(5, Arrays.asList("word5"));
		wordCountMap.put(4, Arrays.asList("word4_1", "word4_2"));
		wordCountMap.put(3, Arrays.asList("word3"));
		wordCountMap.put(2, Arrays.asList("word2"));
		wordCountMap.put(1, Arrays.asList("word1"));
		WikiPageWordFreqResponse respToTest = new WikiPageWordFreqResponse(wordCountMap, "url", "title");
		String outPutString = "URL:" + NEW_LINE + "url" + NEW_LINE + "Title: title" + NEW_LINE + "Top 5 words:"
				+ NEW_LINE + "- 5 word5" + NEW_LINE + "- 4 word4_1, word4_2" + NEW_LINE + "- 3 word3" + NEW_LINE
				+ "- 2 word2" + NEW_LINE + "- 1 word1" + NEW_LINE + "";
		assertEquals(outPutString, respToTest.toString());
	}

}
