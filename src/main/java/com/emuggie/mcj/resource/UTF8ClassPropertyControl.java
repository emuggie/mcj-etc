package com.emuggie.mcj.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class UTF8ClassPropertyControl extends Control{
	protected static final String BUNDLE_EXTENSION = "properties";
	
	public final ResourceBundle newBundle(String baseName	, Locale locale, String format, ClassLoader loader
    		, boolean reload ) throws IllegalAccessException, InstantiationException, IOException{
		
		ResourceBundle parent = null;
		List<Locale> candidateLocal = this.getCandidateLocales(baseName, locale);
		
		if(baseName.contains(".") && locale.equals(candidateLocal.get(candidateLocal.size() - 1))){
			parent = ResourceBundle.getBundle(baseName.substring(0, baseName.lastIndexOf(".")), locale, loader, this);
		}
		
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
        
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        
        if (stream != null) {
            try {
                bundle = new ClassResourceBundle(new InputStreamReader(stream, "UTF-8"), parent);
            } finally {
                stream.close();
            }
        }
        if(bundle == null){
        	bundle = parent;
        }
        if(!baseName.contains(".") && bundle == null && locale.equals(candidateLocal.get(candidateLocal.size() - 1))){
        	bundle = new EmptyBundle();
		}
        return bundle;
    }
}
