package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CityUtilTest {

    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void city_weather_more_than_40_degrees() {
        List<City> cityList = cityRepository.findCitiesByWeatherGreaterThan(40);
        assertTrue(cityList.isEmpty());
    }

    @Test
    public void city_weather_less_than_10_degrees() {
        List<City> cityList = cityRepository.findCitiesByWeatherLessThan(10);
        assertTrue(cityList.isEmpty());
    }

    @Test
    public void city_contains_some() {
        boolean musanze_exists = cityRepository.existsCityByName("Musanze");
        boolean kigali_exists = cityRepository.existsCityByName("Kigali");

        assertTrue(musanze_exists && kigali_exists);
    }

    @Test
    public void testMocking() {
        List<City> mockedList = Mockito.mock(ArrayList.class);
        City city = new City("Musanze", 18);
        mockedList.add(city);
        Mockito.verify(mockedList).add(city);

        assertEquals(0, mockedList.size());
    }

    @Test
    public void testSpying() {
        List<City> spyList = Mockito.spy(ArrayList.class);
        City city = new City("Musanze", 18);
        spyList.add(city);
        Mockito.verify(spyList).add(city);

        assertEquals(1, spyList.size());
    }
}
