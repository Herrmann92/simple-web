package de.herrmanno.simple_web.config.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.herrmanno.simple_web.core.filter.AnnotationFilter;
import de.herrmanno.simple_web.core.filter.Filter;
import de.herrmanno.simple_web.core.filter.RouteFilter;

public class DefaultFilterConfig implements FilterConfig {

	protected Set<AnnotationFilter> aFilter = new HashSet<AnnotationFilter>();
	protected Set<RouteFilter> rFilter = new HashSet<RouteFilter>();


	@Override
	public void register(Filter filter) {
		if(filter instanceof RouteFilter) register((RouteFilter) filter); 
		else if(filter instanceof AnnotationFilter) register((AnnotationFilter) filter);
	}

	protected void register(RouteFilter filter) {
		rFilter.add(filter);
	}

	protected void register(AnnotationFilter filter) {
		aFilter.add(filter);
	}

	@Override
	public Collection<RouteFilter> getRouteFilter() {
		return rFilter;
	}

	@Override
	public Collection<AnnotationFilter> getAnnotationFilter() {
		return aFilter;
	}


}
