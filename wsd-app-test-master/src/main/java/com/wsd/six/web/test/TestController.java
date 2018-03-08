package com.wsd.six.web.test;

import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.wsd.six.service.XmlTestService;

@RestController
@RequestMapping("/test/*")
public class TestController {

	private static final String INVALID = "invalid";

	private static final String VALID = "valid";

	private static final String REGX_SCHEMA = "/schema/RegXChangeXmlSuperSchema-v0-0-2.xsd";

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	public XmlTestService testService;

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public @ResponseBody String testPing() {
		return "OK";
	}

	@RequestMapping(value = "/service", method = RequestMethod.GET)
	public @ResponseBody Object testService() throws Exception {
		return testService.doSomething();
	}

	@RequestMapping(value = "/validateXML", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String validateXMLAgaistRegxSchema(@RequestBody String xml) throws SAXException, IOException {
		final URL schemaURL = TestController.class.getResource(REGX_SCHEMA);
		final boolean valid = testService.validateAgainstRegxSchema(xml, schemaURL);
		logger.info("xml validation result:", valid);
		String msg = null;
		if (valid) {
			msg = VALID;
		} else {
			msg = INVALID;
		}
		return new ObjectMapper().writeValueAsString(msg);
	}

	@RequestMapping(value = "/risk", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String findRisk(@RequestBody String xml)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {
		return testService.findRisk(xml);

	}

}
