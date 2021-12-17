package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.controller.CityController;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(CityController.class)
public class CityServiceTest {

    //integration tests

    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;

    //success
    @Test
    public void getAll_success() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(new City(101,"Kigali",24,75.2),
                new City(104,"Nyagatare",28, 82.4)));
        assertEquals("Kigali",cityService.getAll().get(0).getName());
    }

    @Test
    public void getById_success() {
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new City(104,"Nyagatare",28, 82.4)));
        assertEquals("Nyagatare", cityService.getById(104).get().getName());
    }

    @Test
    public void save_success() {
        when(cityRepositoryMock.save(any(City.class))).thenReturn(new City(105,"Muhanga",23, 73.4));
        assertEquals("Muhanga", cityService.save(new CreateCityDTO()).getName());
    }

    @Test
    public void exists_by_name_true() {
        when(cityRepositoryMock.existsByName(anyString())).thenReturn(true);
        assertEquals(true, cityService.existsByName("Musanze"));
    }

    //fail
    @Test
    public void getAll_empty() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList());
        assertEquals(true,cityService.getAll().isEmpty());
    }

    @Test
    public void getById_404() {
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertEquals(true, !cityService.getById(400).isPresent());
    }

    @Test
    public void exists_by_name_false() {
        when(cityRepositoryMock.existsByName(anyString())).thenReturn(false);
        assertEquals(false, cityService.existsByName("Nyanza"));
    }



}