package com.wsd.six.test.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.wsd.six.service.XmlTestService;
import com.wsd.six.web.test.TestController;

public class ContollerTest {

	private MockMvc mockMVC;

	@Mock
	private XmlTestService xmlTestService;

	@InjectMocks
	private TestController testController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMVC = MockMvcBuilders.standaloneSetup(testController).build();
	}

	@Test
	public void testRisk_EndPoint() throws Exception {
		when(xmlTestService.findRisk(any(String.class))).thenReturn(any(String.class));
		final XML xml = new XMLDocument(ContollerTest.class.getClassLoader().getResourceAsStream(("sample.xml")));
		
		 mockMVC.perform(MockMvcRequestBuilders.post("/test/risk")
				.contentType(MediaType.APPLICATION_XML)
				.content(xml.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print());
		 
		 verify(xmlTestService, times(1)).findRisk(any(String.class));
		 verifyNoMoreInteractions(xmlTestService);
	}
	
	@Test
	public void testValidateXML_EndPoint_With_Valid_XML() throws Exception {
		when(xmlTestService.validateAgainstRegxSchema(any(String.class),any(URL.class))).thenReturn(true);
		final XML xml = new XMLDocument(ContollerTest.class.getClassLoader().getResourceAsStream(("sample.xml")));
		 mockMVC.perform(MockMvcRequestBuilders.post("/test/validateXML")
				.contentType(MediaType.APPLICATION_XML)
				.content(xml.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", is("valid")))
				.andDo(print());
		
		 verify(xmlTestService, times(1)).validateAgainstRegxSchema(any(String.class),any(URL.class));
		 verifyNoMoreInteractions(xmlTestService);
	}
	
	@Test
	public void testValidateXML_EndPoint_With_Invalid_XML() throws Exception {
		when(xmlTestService.validateAgainstRegxSchema(any(String.class),any(URL.class))).thenReturn(false);
		final XML xml = new XMLDocument(ContollerTest.class.getClassLoader().getResourceAsStream(("invalid.xml")));
		
		
		 mockMVC.perform(MockMvcRequestBuilders.post("/test/validateXML")
				.contentType(MediaType.APPLICATION_XML)
				.content(xml.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", is("invalid")))
				.andDo(print());
		 
		 verify(xmlTestService, times(1)).validateAgainstRegxSchema(any(String.class),any(URL.class));
		 verifyNoMoreInteractions(xmlTestService);
	}

}
