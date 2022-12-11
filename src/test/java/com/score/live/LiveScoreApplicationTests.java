package com.score.live;


import com.score.live.controller.ScoreController;
import com.score.live.dto.NewsSummaryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = LiveScoreApplication.class
)

@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ComponentScan(basePackages = "com.score.live.*")
//@Import(ScoreController.class)
class LiveScoreApplicationTests {


	@LocalServerPort
	private Integer  port;


	private RestTemplate restTemplate;

	//@Autowired
	private MockMvc mockMvc;

	private String BASE_URL="http://localhost";

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ScoreController scoreController;

	@Test
	void contextLoads() {
		Assertions.assertNotEquals(scoreController, null);
	}

	@BeforeEach
	public void init(){

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		restTemplate= new RestTemplate();

		BASE_URL= BASE_URL.concat(":")
				.concat(String.valueOf(port))
				.concat("/live-score-service")
				.concat("/api/score");
		System.out.println("baseUrl:"+ BASE_URL);
	}


	@Test
	void insertNewsItem() throws Exception {

		//insert reocord

		/* ResultActions res= mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						//.content("{\"channelTitle\": \"test\"}"))
				)
				.andExpect(status().isCreated());*/

		ResponseEntity  res= restTemplate.postForEntity(BASE_URL, null, Boolean.class);
		Assertions.assertEquals(res.getStatusCode(), HttpStatus.CREATED);

	}

	@Test
	void insertAndFetchDashboardData(){
		ResponseEntity  postResp= restTemplate.postForEntity(BASE_URL, null, Boolean.class);

		Assertions.assertEquals(postResp.getStatusCode(), HttpStatus.CREATED);

		ResponseEntity<List<NewsSummaryDto>> fetchResp= restTemplate.exchange(
				BASE_URL + "/search", HttpMethod.GET, null, new ParameterizedTypeReference<List<NewsSummaryDto>>(){}
		);

		Assertions.assertNotNull(fetchResp.getStatusCode(), String.valueOf(HttpStatus.OK));

	}

	@Test
	void fetchWithoutInsertion(){
		ResponseEntity<List<NewsSummaryDto>> fetchResp= restTemplate.exchange(
				BASE_URL + "/search", HttpMethod.GET, null, new ParameterizedTypeReference<List<NewsSummaryDto>>(){}
		);

		Assertions.assertEquals(fetchResp.getBody().size(), 0);
	}



}
