package com.wsd.six.web;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.xml.sax.SAXException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ControllerErrorResponse handleException(Exception e) {
		logger.error("Exception", e);
		return new ControllerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"An error occurred.  [" + e.getMessage() + "].  Please check log files.");
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ControllerErrorResponse handleControlleException(ControllerException e) {
		logger.error("ControllerException", e);
		return new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	ControllerErrorResponse handleAccessDeniedException(AccessDeniedException e) {
		logger.error("AccessDeniedException", e);
		return new ControllerErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
	}

	@ExceptionHandler(SAXException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ControllerErrorResponse handleAccessDeniedException(SAXException e) {
		logger.error("SAXException", e);
		return new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ExceptionHandler(JAXBException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ControllerErrorResponse handleAccessDeniedException(JAXBException e) {
		logger.error("JAXBException", e);
		return new ControllerErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

}
