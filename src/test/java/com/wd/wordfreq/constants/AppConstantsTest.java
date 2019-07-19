package com.wd.wordfreq.constants;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.wd.wordfreq.constants.AppConstants;

public class AppConstantsTest {

	@Test
	public void test() throws NoSuchMethodException, SecurityException {
		Constructor<AppConstants> ctor = AppConstants.class.getDeclaredConstructor((Class[]) null);
		ctor.setAccessible(true);
		try {
			ctor.newInstance((Object[]) null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			assertSame(InvocationTargetException.class, e.getClass());
		}
	}

}
