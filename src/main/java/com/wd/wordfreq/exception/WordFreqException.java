package com.wd.wordfreq.exception;
/**
 * RTE class which represents issues while calculating 
 * most frequent words 
 * @author RakeshKShukla
 */
public class WordFreqException extends RuntimeException
{
	private static final long serialVersionUID = -4277296315119253106L;
	/**
	 * Constructs WordFrequencyException instance with passed message
	 * @param message error message
	 */
	public WordFreqException( String message)
	{
		super(message);
	}

}
