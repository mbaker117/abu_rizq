package com.mbaker.abumazrouqdashboard.validator;

import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonValidator {

	private final static Logger LOG = LoggerFactory.getLogger(CommonValidator.class);

	public void validateEmptyString(String value, String msg, String serviceName) {
		if (Strings.isBlank(value)) {
			final IllegalArgumentException ex = new IllegalArgumentException(msg+" is null or empty");
			LOG.error("[{}]: {}",serviceName,ex.getMessage());
			throw ex;
		}
	}

	public void validateEmptyObject(Object value, String msg, String serviceName) {
		if (Objects.isNull(value)) {
			final IllegalArgumentException ex = new IllegalArgumentException(msg + " is null");
			LOG.error("[{}]: {}", serviceName, ex.getMessage());
			throw ex;
		}

	}
}
