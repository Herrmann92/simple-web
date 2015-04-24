package de.herrmanno.simple_web.parameterhandler;

public class StringParameterHandler implements ParameterHandler<String> {

	@Override
	public String regex() {
		return "[\\S]+";
	}

	@Override
	public String handle(String str) {
		return str;
	}

}
