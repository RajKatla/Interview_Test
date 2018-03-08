package com.wsd.six.test.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.NestedServletException;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ContextConfiguration({"classpath:spring-app.xml"})
public class ControllerIntegrationTest {
	
	@Autowired
    private WebApplicationContext wac;
 
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
        		.dispatchOptions(true)
        		.build();
    }
    
    @Test
    public void testPing() throws Exception {
        this.mockMvc
        .perform(get("/test/ping"))
        .andExpect(status().isOk())
        .andDo(print());
    }
    
    @Test
    public void testRisk_EndPoint() throws Exception {
		final XML xml = new XMLDocument(ControllerIntegrationTest.class.getClassLoader().getResourceAsStream(("sample.xml")));
        this.mockMvc
                .perform(post("/test/risk")
        		.contentType(MediaType.APPLICATION_XML_VALUE).content(xml.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.01100MRM", is("1")))
 			    .andExpect(jsonPath("$.01110CRM", is("1")))
 			    .andExpect(jsonPath("$.01120RecommendedHoldingPeriod", is(1.0)));
        
    }
    
    @Test
    public void testValidateXML_EndPoint_With_Valid_XML() throws Exception {
		final XML xml = new XMLDocument(ControllerIntegrationTest.class.getClassLoader().getResourceAsStream(("sample.xml")));
        this.mockMvc
                .perform(post("/test/validateXML")
        		.contentType(MediaType.APPLICATION_XML_VALUE)
        		.content(xml.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("valid")));
        
    }
    
    @Test(expected = NestedServletException.class)
    public void testValidateXML_EndPoint_With_Invalid_XML() throws Exception {
		final XML xml = new XMLDocument(ControllerIntegrationTest.class.getClassLoader().getResourceAsStream(("invalid.xml")));
        this.mockMvc
                .perform(post("/test/validateXML")
        		.contentType(MediaType.APPLICATION_XML_VALUE)
        		.content(xml.toString()));
        
    }
}
