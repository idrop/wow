package wow.web;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wow.web.dto.SessionDTO;
import wow.web.dto.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static wow.web.WOWController.API_SESSION;
import static wow.web.WOWController.API_USER;

@ExtendWith(SpringExtension.class)
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
    @DisplayName("Example Service should work!")
    public void whenANewSessionIsCreatedTheSessionIdIsReturned() {
        ResponseEntity<SessionDTO> entity = createNewSession();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getId()).isNotBlank();
    }

    @Test
    public void whenSessionCreatedAUserCanBeAdded() {
        ResponseEntity<SessionDTO> newSession = createNewSession();
        String sessionId = newSession.getBody().getId();
        UserDTO userDTO = new UserDTO();
        userDTO.setSessionId(sessionId);
        userDTO.setName("phil");
        ResponseEntity<UserDTO> userResponseEntity = testRestTemplate.postForEntity(getUrl(API_USER), userDTO, UserDTO.class);
        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDTO user = userResponseEntity.getBody();
        String userName = user.getName();
        assertThat(userName).isEqualTo("phil");
    }

    @Test
    public void whenASessionIsCreatedYouCanGetThatSessionWithItsId() {
        ResponseEntity<SessionDTO> entity = createNewSession();
        String sessionId = entity.getBody().getId();
        String url = getUrl(API_SESSION + "/" + sessionId);
        entity = testRestTemplate.getForEntity(url, SessionDTO.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getCurrency()).isEqualTo("GBP");

    }

    private ResponseEntity<SessionDTO> createNewSession() {
        String url = getUrl(API_SESSION);
        SessionDTO request = new SessionDTO();
        request.setCurrency("GBP");
        request.setName("dinner");
        return this.testRestTemplate.postForEntity(url, request, SessionDTO.class);
    }

    private String getUrl(String path) {
        return "http://localhost:" + this.port + path;
    }


}