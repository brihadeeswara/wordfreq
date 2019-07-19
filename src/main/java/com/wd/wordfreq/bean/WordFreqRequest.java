package com.wd.wordfreq.bean;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
/**
 * Abstract class representing word frequency calculation request
 * This class is abstract because it does not have any hint about actual text
 * @author RakeshKShukla
 *
 */
@Getter
@AllArgsConstructor
@ToString
public abstract class WordFreqRequest 
{
	private int maxTopWords;
	private Optional<String> wordRegex = Optional.empty();
}
