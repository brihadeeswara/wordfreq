package com.wd.wordfreq.bean.wiki;

import java.util.Optional;

import com.wd.wordfreq.bean.WordFreqRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents word frequency calculation request from wiki page
 * @author RakeshKShukla
 */
@Getter
@Setter
@ToString
public class WikiPageWordFreqRequest extends WordFreqRequest 
{
	/**
	 * page id of wiki page
	 */
	private int pageId;
	/**
	 * Constructor 
	 * @param maxTopWords 
	 * @param wordRegex
	 * @param pageId
	 */
	@Builder
	public WikiPageWordFreqRequest(int maxTopWords, Optional<String> wordRegex,int pageId)
	{
		super(maxTopWords,wordRegex);
		this.pageId = pageId;
	}
}