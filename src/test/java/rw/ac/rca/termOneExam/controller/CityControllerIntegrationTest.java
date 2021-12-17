package rw.ac.rca.termOneExam.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAll_success() {
        String response = this.testRestTemplate.getForObject("/api/cities/all", String.class);
        assertEquals("[{\"id\":101,\"name\":\"Kigali\",\"weather\":24.0,\"fahrenheit\":0.0},{\"id\":102,\"name\":\"Musanze\",\"weather\":18.0,\"fahrenheit\":0.0},{\"id\":103,\"name\":\"Rubavu\",\"weather\":20.0,\"fahrenheit\":0.0},{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28.0,\"fahrenheit\":0.0}]", response);
    }

    @Test
    public void getById_success() {
        City city = this.testRestTemplate.getForObject("/api/cities/id/101",City.class);
        assertEquals("Kigali", city.getName());
        assertEquals(24, city.getWeather());
    }

    @Test
    public void getById_404() {
        ResponseEntity<APICustomResponse> response = this.testRestTemplate.getForEntity("/api/cities/id/113", APICustomResponse.class);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("City not found with id 113", response.getBody().getMessage());
    }

    @Test
    public void save_success() {

        CreateCityDTO newCity = new CreateCityDTO();
        newCity.setName("Huye");
        newCity.setWeather(25);

        ResponseEntity<City> response = this.testRestTemplate.postForEntity("/api/cities/add", newCity, City.class);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Huye",response.getBody().getName());
        assertEquals(25,response.getBody().getWeather());
    }

    @Test
    public void save_failure() {

        CreateCityDTO newCity = new CreateCityDTO();
        newCity.setName("Kigali");
        newCity.setWeather(10);

        ResponseEntity<APICustomResponse> response = this.testRestTemplate.postForEntity("/api/cities/add",newCity, APICustomResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("City name Kigali is registered already", response.getBody().getMessage());
    }
}
