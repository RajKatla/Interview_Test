package com.wsd.six.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.wsd.six.eptemt.PriipList;
import com.wsd.six.eptemt.PriipList.Priip.PriipData.Risk;

@Service("testService")
public class XmlTestServiceImpl implements XmlTestService {

	@Override
	public String doSomething() {
		return "something";
	}

	public boolean validateAgainstRegxSchema(final String xml, final URL xsd) throws SAXException, IOException {
		try {
			final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			final Schema schema = factory.newSchema(xsd);
			final Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xml)));
		} catch (IOException e) {
			throw e;
		} catch (SAXException e) {
			throw e;
		}
		return true;
	}

	public String findRisk(final String xml) throws JAXBException, JsonGenerationException, JsonMappingException, IOException {
		final JAXBContext jaxbContext = JAXBContext.newInstance(PriipList.class);
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		final PriipList priipList = (PriipList) unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
		final Risk risk = Stream.of(priipList.getPriip()).iterator().next().iterator().next().getPriipData().getRisk();
		return new ObjectMapper().writeValueAsString(risk);

	}

}
