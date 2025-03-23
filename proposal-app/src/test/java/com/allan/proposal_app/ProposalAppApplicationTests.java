package com.allan.proposal_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProposalAppApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	void contextLoads() {
		// necessary: docker start postgres-container
		// This test will fail if the application context does not start
	}

	@Test
	void mainMethodRunsWithoutException() {
		ProposalAppApplication.main(new String[]{});
	}

	@Test
	void applicationStartsOnRandomPort() {
		assertThat(port).isGreaterThan(0);
	}

}
