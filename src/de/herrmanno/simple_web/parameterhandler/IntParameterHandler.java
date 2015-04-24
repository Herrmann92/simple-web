package de.herrmanno.simple_web.parameterhandler;

public class IntParameterHandler implements ParameterHandler<Integer> {

	@Override
	public String regex() {
		return "[0-9]+";
	}

	@Override
	public Integer handle(String str) {
		return Integer.parseInt(str);
	}
	
	@Override
	public Class<? extends Integer> getHandledType() {
		return int.class;
	}

}
