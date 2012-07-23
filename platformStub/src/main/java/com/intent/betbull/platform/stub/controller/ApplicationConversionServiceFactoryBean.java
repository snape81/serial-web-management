package com.intent.betbull.platform.stub.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
        registry.addConverter(new DateToStringConverter());
        registry.addConverter(new StringToDateConverter());
	}

    private class DateToStringConverter implements Converter<Date,String>{

        @Override
        public String convert(Date date) {
            return sdf.format(date);

        }
    }

    private class StringToDateConverter implements Converter<String,Date> {

        @Override
        public Date convert(String s) {
            try {
                return sdf.parse(s);
            } catch (ParseException e) {
                return null;
            }
        }
    }
}
