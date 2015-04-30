package de.herrmanno.simple_web.config.parameter;

import java.net.URI;
import java.net.URISyntaxException;


public enum MethodParameter {
	
	INT(int.class, 0, "[\\d]+", Converters.INT),
	STRING(String.class, 1, "[^/\\s]+", Converters.STRING),
	URI(URI.class, 2, "[^\\s]+", Converters.URI);
	
	public final String regex;
	public final int precedence;
	public final Class<?> clazz;
	public final Converter converter;

	private MethodParameter(Class<?> clazz, int precedence, String regex, Converter converter) {
		this.clazz = clazz;
		this.precedence = precedence;
		this.regex = regex;
		this.converter = converter;
	}
	
	public static MethodParameter get(Class<?> c) {
		for(MethodParameter mp : MethodParameter.values()) {
			if(c.equals(mp.clazz))
				return mp;
		}
		return null;
	}
	
	public static String getRegex(Class<?> c) {
		MethodParameter mp = get(c);
		if(mp != null)
			return mp.regex;
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Class<? extends T> c, String value) {
		MethodParameter mp = get(c);
		if(mp != null)
			return (T) mp.converter.get(value);
		else
			return null;
	};

	
	@FunctionalInterface
	private interface Converter {
		public Object get(String str);
	}
	
	private static class Converters {
		private final static Converter INT = (str)->Integer.parseInt(str);
		private final static Converter STRING = (str)->str;
		private final static Converter URI = new Converter() {
			public Object get(String str) {
					try {
						return new URI(str);
					} catch (URISyntaxException e) {
						e.printStackTrace();
						return null;
					}
			}
		};
	}

}
