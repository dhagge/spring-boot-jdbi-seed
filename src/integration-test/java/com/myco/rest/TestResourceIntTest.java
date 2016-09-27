package com.myco.rest;

import com.myco.IntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Sample int test for TestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@Slf4j
public class TestResourceIntTest {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate template = new TestRestTemplate();
    private URI testUri;

    @Before
    public void before() throws Exception {
        testUri = new URI("http://localhost:" + port);
    }

    @Test
    public void test_get() throws Exception {
        ResponseEntity<String> response = template.getForEntity(testUri + "/test", String.class);
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), equalTo("Hello there, this is a test!")); // the value from the test DB
    }

    @Test
    public void test_post() throws Exception {
        ResponseEntity<String> response = template.postForEntity(testUri + "/test", null, String.class);
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), equalTo("Successful POST"));
    }
}