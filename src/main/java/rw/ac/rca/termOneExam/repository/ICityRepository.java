package rw.ac.rca.termOneExam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rw.ac.rca.termOneExam.domain.City;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {

	boolean existsByName(String name);

	List<City> findCitiesByWeatherGreaterThan(double weather);

	List<City> findCitiesByWeatherLessThan(double weather);

	boolean existsCityByName(String name);

}
