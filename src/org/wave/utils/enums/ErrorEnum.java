package org.wave.utils.enums;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ErrorEnum {
	
	NULL_FILE("error.message.nullFile"), 
	DIFFERENT_CLASSES("error.message.differentClasses"), 
	NOT_COLLECTION_FIELD("error.message.notCollectionField");
	
	private String key;

	private ErrorEnum(String key) {
		this.key = key;
	}
	
	public String getMessage(Object... params) {
		ResourceBundle bundle = ResourceBundle.getBundle("org.wave.utils.messages.messages", Locale.getDefault());

		String value = bundle.getString(this.key);
		
		return new MessageFormat(value).format(params);
	}

}
