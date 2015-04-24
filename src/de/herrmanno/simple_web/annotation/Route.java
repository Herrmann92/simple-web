package de.herrmanno.simple_web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.herrmanno.simple_web.constants.HTTP_METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Route {
	String regex() default "";
	String route() default "";
	HTTP_METHOD[] methods() default {};
}
