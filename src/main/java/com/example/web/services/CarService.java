package com.example.web.services;

import com.example.web.conf.ent.Car;
import com.example.web.repos.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Page<Car> getAllCars(Pageable pageable){
     return carRepository.findAll(pageable);
    }
}
