package de.herrmanno.simple_web.config.filter;

import java.util.Collection;

import de.herrmanno.simple_web.core.filter.AnnotationFilter;
import de.herrmanno.simple_web.core.filter.Filter;
import de.herrmanno.simple_web.core.filter.RouteFilter;

public interface FilterConfig {

	public Collection<RouteFilter> getRouteFilter();
	
	public Collection<AnnotationFilter> getAnnotationFilter();
	
	
	public void register(Filter filter);
	
	default public void register(Filter... filter) {
		for(Filter f : filter)
			register(f);
	}
}
