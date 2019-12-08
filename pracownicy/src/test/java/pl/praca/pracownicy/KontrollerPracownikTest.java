package pl.praca.pracownicy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import pl.praca.pracownicy.model.ModelPracownicy;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PracownicyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KontrollerPracownikTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllEmployees() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/pracownicy",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetEmployeeById() {
        ModelPracownicy pracownik = restTemplate.getForObject(getRootUrl() + "/pracownicy/1", ModelPracownicy.class);
        System.out.println(pracownik.getFirstName());
        assertNotNull(pracownik);
    }

    @Test
    public void testCreateEmployee() {
        ModelPracownicy pracownik = new ModelPracownicy();
        pracownik.setEmailId("admin@gmail.com");
        pracownik.setFirstName("admin");
        pracownik.setLastName("admin");
        pracownik.setPesel("123456789001");
        pracownik.setAdress("ul. Przykładowa 15");
        pracownik.setTelephoneNumber("658940234");
        ResponseEntity<ModelPracownicy> postResponse = restTemplate.postForEntity(getRootUrl() + "/pracownicy", pracownik, ModelPracownicy.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateEmployee() {
        int id = 1;
        ModelPracownicy pracownik = restTemplate.getForObject(getRootUrl() + "/pracownicy/" + id, ModelPracownicy.class);
        pracownik.setFirstName("admin1");
        pracownik.setLastName("admin2");
        restTemplate.put(getRootUrl() + "/pracownicy/" + id, pracownik);
        ModelPracownicy updatedEmployee = restTemplate.getForObject(getRootUrl() + "/pracownicy/" + id, ModelPracownicy.class);
        assertNotNull(updatedEmployee);
    }

    @Test
    public void testDeleteEmployee() {
        int id = 2;
        ModelPracownicy pracownik = restTemplate.getForObject(getRootUrl() + "/pracownicy/" + id, ModelPracownicy.class);
        assertNotNull(pracownik);
        restTemplate.delete(getRootUrl() + "/pracownicy/" + id);
        try {
            pracownik = restTemplate.getForObject(getRootUrl() + "/pracownicy/" + id, ModelPracownicy.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
