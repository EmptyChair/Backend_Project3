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
        Long key = searchForKey(id);
        return database.get(key);
    }

    @Override
    public List<Car> getAll() {
        //HOMEWORK - done
        List<Car> carList = new ArrayList<>(database.values());
        return carList;
    }

    @Override
    public Car update(Car car) {
        //HOMEWORK - price only - done
        BigDecimal price = car.getPrice();
        Long id = car.getId();
        Long key = searchForKey(id);
        Car newCar = database.get(key);
        newCar.setPrice(price);
        database.put(key, newCar);
        return newCar;
    }

    public void delete(Long id) {
        //HOMEWORK - done
        Long key = searchForKey(id);
        database.remove(key);
    }

    private Long searchForKey(Long id) {

        return -1L;
    }
/*


 */

}
