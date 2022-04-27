package com.example.web.repos;

import com.example.web.conf.ent.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car,Long> {
    Page<Car> findAll(Pageable pageable);

    @Query("Select c from Car c where lower(c.model)  like %:model%")
     List<Car> findAllByModel(String model);
}
