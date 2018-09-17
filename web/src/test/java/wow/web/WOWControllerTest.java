package wow.web;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import wow.web.dto.SessionDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static wow.web.WOWController.API_SESSION;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class WOWControllerTest {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createSession() {
        String url = getUrl(API_SESSION);
        SessionDTO request = new SessionDTO();
        request.setCurrency("GBP");
        request.setName("dinner");
        ResponseEntity<String> entity = this.testRestTemplate.postForEntity(url, request, String.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    private String getUrl(String path) {
        return "http://localhost:" + this.port + path;
    }


}