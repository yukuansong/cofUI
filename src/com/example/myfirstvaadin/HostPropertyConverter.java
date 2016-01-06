package com.example.myfirstvaadin;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class HostPropertyConverter implements Converter<String, HostProperty> {

	@Override
	public HostProperty convertToModel(String value, Class<? extends HostProperty> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		HostProperty property = new HostProperty();
		property.setName(value);
		return null;
	}

	@Override
	public String convertToPresentation(HostProperty value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		
		return value.getName();
	}

	@Override
	public Class<HostProperty> getModelType() {
		// TODO Auto-generated method stub
		return HostProperty.class;
	}

	@Override
	public Class<String> getPresentationType() {
		// TODO Auto-generated method stub
		return String.class;
	}

}
