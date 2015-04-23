package de.herrmanno.simple_web.core.filter;

import java.lang.annotation.Annotation;

public class AnnotationFilter implements Filter {

	public final Class<? extends Annotation> annotation;
	public final FilterFunction function;
	
	public AnnotationFilter(Class<? extends Annotation> annotation, FilterFunction function) {
		this.annotation = annotation;
		this.function = function;
	}
}
