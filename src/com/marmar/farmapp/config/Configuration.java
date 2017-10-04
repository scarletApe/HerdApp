package com.marmar.farmapp.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author JMMC
 */
public class Configuration {
	private ApplicationContext applicationContext;
	private static Configuration provider;

	public Configuration() throws ExceptionInInitializerError {
		try {
			this.applicationContext = new ClassPathXmlApplicationContext("appContex.xml");
		} catch (BeansException ex) {
			System.err.print("error " + ex);
		}
	}

	public synchronized static Configuration getInstance() throws ExceptionInInitializerError {
		Configuration tempProvider;
		if (provider == null) {
			provider = new Configuration();
			tempProvider = provider;
		} else if (provider.getApplicationContext() == null) {
			provider = new Configuration();
			tempProvider = provider;
		} else {
			tempProvider = provider;
		}

		return tempProvider;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
