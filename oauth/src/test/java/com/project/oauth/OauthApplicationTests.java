package com.project.oauth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OauthApplication.class)
@WebAppConfiguration
public class OauthApplicationTests {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy proxy;

	private MockMvc mvc;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(proxy).build();
	}

	private String obtainAccessToken(String code) throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	      params.add("grant_type", "authorization_code");
	      params.add("client_id", "thirdPartyClient");
	      params.add("code", code);
	      params.add("redirect_uri", "http://localhost:8090/showEmployees");

	      String base64ClientCredentials = new String(Base64.encodeBase64("thirdPartyClient:secret".getBytes()));


	      ResultActions result
	            = mvc.perform(post("/oauth/token")
	            .params(params)
	            .header("Authorization","Basic " + base64ClientCredentials)
	            .accept("application/json;charset=UTF-8"))
	            .andExpect(status().isOk());

	      String resultString = result.andReturn().getResponse().getContentAsString();

	      JacksonJsonParser jsonParser = new JacksonJsonParser();
	      return jsonParser.parseMap(resultString).get("access_token").toString();

		
	}

	@Test
	public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
		mvc.perform(get("/getEmployeesList/").param("empId", "EMP1234")).andExpect(status().isUnauthorized());
	}

	@Test
	public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
		String accessToken = obtainAccessToken("rUQN85");
		mvc.perform(
				get("/getEmployeesList/").header("Authorization", "Bearer " + accessToken).param("empId", "EMP1234"))
				.andExpect(status().isForbidden());
	}

	@Test
	public void givenToken_whenPostGetSecureRequest_thenOk() throws Exception {
		String accessToken = obtainAccessToken("WXo09");

		String employeeString = "{\"empId\":\"EMP1234\",\"empName\":\"Debjani\"}";

		mvc.perform(post("/getEmployeesList/").header("Authorization", "Bearer " + accessToken)
				.contentType("application/json;charset=UTF-8").content(employeeString)
				.accept("application/json;charset=UTF-8")).andExpect(status().isCreated());

		mvc.perform(get("/getEmployeesList/").param("empId", "EMP1234").header("Authorization", "Bearer " + accessToken)
				.accept("application/json;charset=UTF-8")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.empName", is("Debjani")));
	}

	@Test
	public void contextLoads() {
	}

}
