package com.emuggie.mcj.resource;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class EmptyBundle extends ResourceBundle{
	private boolean hasParent = false;
	
	public EmptyBundle(){
		
	};
	
	public EmptyBundle(ResourceBundle parent){
		this.setParent(parent);
		hasParent= true;
	};
	
	@Override
	protected Object handleGetObject(String key) {
		if(hasParent){
			return null;
		}
		return "!!NotFound["+key+"]";
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(keySet());
	}

}
