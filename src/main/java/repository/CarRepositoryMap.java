package repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.Car;

public class CarRepositoryMap implements CarRepository {

    private Map<Long, Car> database = new HashMap<>();
    private long currentId;

    @Override
    public Car save(Car car) {
        car.setId(++currentId);
        database.put(currentId, car);
        return car;
    }

    @Override
    public Car getById(Long id) {
        return database.get(id);
    }

    @Override
    public List<Car> getAll() {
        //HOMEWORK - done
        List<Car> carList = new ArrayList<>(database.values());
        return carList;
    }

    @Override
    public Car update(Car car) {
        //HOMEWORK - becomes a fully described car with id - changes price only - done
        BigDecimal price = car.getPrice();
        Long id = car.getId();
        Car newCar = database.get(id);
        newCar.setPrice(price);
        //database.put(id, newCar);
        return newCar;
    }

    public void delete(Long id) {
        //HOMEWORK - done
        database.remove(id);
    }

}
