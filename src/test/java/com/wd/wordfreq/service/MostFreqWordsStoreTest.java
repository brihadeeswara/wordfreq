package com.wd.wordfreq.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.wd.wordfreq.service.MostFreqWordsStore;

public class MostFreqWordsStoreTest {

	@Test
	public void testAddWordWithCountToStore() {
		MostFreqWordsStore store = new MostFreqWordsStore();
		store.setMaxWords(5);
		store.addWordWithCountToStore(5, "word5_1");
		store.addWordWithCountToStore(5, "word5_2");
		store.addWordWithCountToStore(4, "word4");
		store.addWordWithCountToStore(3, "word3");
		store.addWordWithCountToStore(2, "word2");
		store.addWordWithCountToStore(1, "word1");
		assertEquals("word5_1", store.getMap().get(5).get(0));
		assertEquals("word5_2", store.getMap().get(5).get(1));
		assertEquals("word4", store.getMap().get(4).get(0));
		assertEquals("word3", store.getMap().get(3).get(0));
		assertEquals("word2", store.getMap().get(2).get(0));
		assertEquals("word1", store.getMap().get(1).get(0));

	}

	
}
