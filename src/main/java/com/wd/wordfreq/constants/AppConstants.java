package com.wd.wordfreq.constants;
/**
 * Stores constants that needs to be shared among type
 * @author RakeshKShukla
 *
 */
public class AppConstants 
{
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String COMMAND_LINE_ARGS_PAGEID = "pageid=";
	public static final String COMMAND_LINE_ARGS_WORDREGEX = "regex=";
	public static final String COMMAND_LINE_ARGS_MAXTOPWORDS = "maxwords=";
	public static final int DEFAULT_PAGEID = 21721040;
	public static final int DEFAULT_MAXTOPWORDS = 5;
	public static final String DEFAULT_WORDREGEX = "^[a-z]{4,}$";
	public static final String QUERY_PARAM_PAGEIDS= "pageids";
	private AppConstants()
	{
		throw new IllegalArgumentException("Not supposed to be called");
	}
}
