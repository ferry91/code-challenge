package com.adidas.subscriptions;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Application config class to configure beans initialization.
 */
@Configuration
public class ApplicationConfig {

	/**
	 * Locale initialization.
	 *
	 * @return the locale resolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
		return sessionLocaleResolver;
	}
	
	@Bean
	public ModelMapper modelMapper() {
	   ModelMapper modelMapper = new ModelMapper();
	   modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	   return modelMapper;
	}
}

