package service;

import domain.Car;

import java.util.List;

public interface CarService {

    //CRUD OPERATIONS - create, read, update, delete

    Car save(Car car);

    Car getById(Long id);

    List<Car> getAll();

    Car update(Car car);

    void delete(Long id);
}
