package com.wd.wordfreq.bean.wiki;

import static com.wd.wordfreq.constants.AppConstants.*;

import java.util.List;
import java.util.Map;

import com.wd.wordfreq.bean.WordFreqResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * Represents response of word frequency calculation for wiki page
 * @author RakeshKShukla
 *
 */
@Getter
@Setter
public class WikiPageWordFreqResponse extends WordFreqResponse 
{
	/**
	 * Url of page
	 */
	private String url;
	/**
	 * title in json response
	 */
	private String title;
	/**
	 * Constructor
	 * @param topWords map containing top words
	 * @param url wiki page url
	 * @param title wiki page title as in json response
	 */
	@Builder
	public WikiPageWordFreqResponse(Map<Integer, List<String>> topWords,String url, String title) {
		super(topWords);
		this.url = url;
		this.title = title;
	}
	/**
	 * @see Object#toString()
	 * This method is used to output result when command line is used
	 */
	@Override
	public String toString() {
			StringBuilder toReturn = new StringBuilder("URL:");
			toReturn.append(NEW_LINE).append(url).append(NEW_LINE).append("Title: ").append(title).toString();
			toReturn.append(NEW_LINE).append("Top ").append(topWords.size()).append(" words:").append(NEW_LINE);
			topWords.entrySet().stream().forEach(entry -> {
				toReturn.append("- ").append(entry.getKey());
				entry.getValue().stream().forEach(e -> toReturn.append(" ").append(e).append(","));
				toReturn.deleteCharAt(toReturn.length() - 1);
				toReturn.append(NEW_LINE);
			});
		return toReturn.toString();
	}
}
