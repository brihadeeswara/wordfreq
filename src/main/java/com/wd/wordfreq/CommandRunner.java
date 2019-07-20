package com.wd.wordfreq;

import static com.wd.wordfreq.constants.AppConstants.COMMAND_LINE_ARGS_MAXTOPWORDS;
import static com.wd.wordfreq.constants.AppConstants.COMMAND_LINE_ARGS_PAGEID;
import static com.wd.wordfreq.constants.AppConstants.COMMAND_LINE_ARGS_WORDREGEX;
import static com.wd.wordfreq.constants.AppConstants.DEFAULT_MAXTOPWORDS;
import static com.wd.wordfreq.constants.AppConstants.DEFAULT_PAGEID;
import static com.wd.wordfreq.constants.AppConstants.DEFAULT_WORDREGEX;
import static java.util.Optional.empty;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.wd.wordfreq.bean.wiki.WikiPageWordFreqRequest;
import com.wd.wordfreq.constants.AppConstants;
import com.wd.wordfreq.exception.WordFreqException;
import com.wd.wordfreq.service.WordFreqCalc;

@Profile("!test")
@Component
public class CommandRunner {
	
	/**
	 * Autowire service , currently we have only one implementation
	 * which gets data from Wiki page
	 */
	@Autowired
	private WordFreqCalc svc;
	/**
	 * Bean to be used while running this jar from command line,
	 * This will write output to stdout or stderr
	 * @return CommandLineRunner
	 */
	@Bean
	public CommandLineRunner run() {
		return args -> {
			try {
				Optional<Integer> pageid = empty();
				Optional<Integer> topCount = empty();
				Optional<String> wordRegex = Optional.of(DEFAULT_WORDREGEX);
				for (String arg : args) {
					if (arg.startsWith(AppConstants.COMMAND_LINE_ARGS_PAGEID)) {
						pageid = Optional
								.of(Integer.parseInt(arg.substring(COMMAND_LINE_ARGS_PAGEID.length(), arg.length())));
					} else if (arg.startsWith(AppConstants.COMMAND_LINE_ARGS_MAXTOPWORDS)) {
						int topCountArg= Integer.parseInt(arg.substring(COMMAND_LINE_ARGS_MAXTOPWORDS.length(), arg.length()));
						if( topCountArg <= 0 )
						{
				                   System.err.println("Usage:Issue with command line args" + Arrays.toString(args));
						   System.exit(1);
						}
						topCount = Optional.of(topCountArg);
					} else if (arg.startsWith(AppConstants.COMMAND_LINE_ARGS_WORDREGEX)) {
						wordRegex = Optional.of(arg.substring(COMMAND_LINE_ARGS_WORDREGEX.length(), arg.length()));
					}
				}
				System.out.println(svc.getMostFrequentWords(WikiPageWordFreqRequest.builder()
						.maxTopWords(topCount.orElse(DEFAULT_MAXTOPWORDS)).wordRegex(wordRegex).pageId(pageid.orElse(DEFAULT_PAGEID)).build()));
			} catch (IndexOutOfBoundsException | PatternSyntaxException|NumberFormatException ex) {
				System.err.println("Usage:Issue with command line args" + Arrays.toString(args));
				System.exit(1);
			}
			catch( WordFreqException ex)
			{
				System.err.println("Issue while calculating most frequent words:" + ex.getMessage());
				ex.printStackTrace();
				System.exit(2);
			}
		};
	}
}
