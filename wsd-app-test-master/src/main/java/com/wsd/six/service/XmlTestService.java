package com.wsd.six.service;

import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.xml.sax.SAXException;

import com.wsd.six.eptemt.PriipList.Priip.PriipData.Risk;

public interface XmlTestService {

    String doSomething();
    
    boolean validateAgainstRegxSchema(String xml, URL xsd) throws SAXException, IOException;
    
    String findRisk(String xml) throws JAXBException, JsonGenerationException, JsonMappingException, IOException;

}
