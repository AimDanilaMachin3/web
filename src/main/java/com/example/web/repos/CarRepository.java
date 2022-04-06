package com.example.web.repos;

import com.example.web.conf.ent.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car,Long> {
    Page<Car> findAll(Pageable pageable);
}
