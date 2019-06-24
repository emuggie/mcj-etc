package com.emuggie.mcj.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ClassResourceBundle extends PropertyResourceBundle{
	protected static final Control UTF8_CONTROL = new UTF8ClassPropertyControl();
	
	public ClassResourceBundle(InputStream stream) throws IOException {
		super(stream);
	}

	public ClassResourceBundle(InputStreamReader inputStreamReader) throws IOException {
		super(inputStreamReader);
	}
	
	public ClassResourceBundle(InputStreamReader inputStreamReader, ResourceBundle parent) throws IOException {
		this(inputStreamReader);
		if(parent != null && this.parent == null)
			this.setParent(parent);
	}
	
	@Override
	public Object handleGetObject(String key) {
		Object value =super.handleGetObject(key);
		if(value == null && this.parent == null)
			value = "!!NotFound:["+key+"]";
		return value;
	}
	
	public static ResourceBundle getBundle(Class<?> clazz, Locale locale){
		return ResourceBundle.getBundle(clazz.getName(), locale, clazz.getClassLoader(), UTF8_CONTROL); 
	}
	
	public static ResourceBundle getBundle(Object target, Locale locale){
		if(target == null){
			return new EmptyBundle();
		}
		return getBundle(target.getClass(), locale);
	}
	
	public static final String toResourceName(String bundleName, String suffix) {
        StringBuilder sb = new StringBuilder(bundleName.length() + 1 + suffix.length());
        sb.append(bundleName.replace('.', '/')).append('.').append(suffix);
        return sb.toString();
    }
	
}
