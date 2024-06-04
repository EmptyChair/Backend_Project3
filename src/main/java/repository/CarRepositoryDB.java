package repository;

import constants.Constants;
import domain.Car;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static constants.Constants.*;

//import static constants.Constants.*;

public class CarRepositoryDB implements CarRepository  {

    private Connection getConnection() {
        try {
            //static import constants: click on constants, alt+enter
            Class.forName(DB_DRIVER_PATH);
            // http://10.1.2.3:8080/products?id=3
            // jdbc:postgresql://localhost:5432/g_40_cars?user=postgres&password=qwerty007

            String dbUrl = String.format("%s%s?user=%s&password=%s",
                    DB_ADDRESS,DB_NAME,DB_USERNAME,DB_PASSWORD);
            return DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car save(Car car) {
        return null;
    }

    @Override
    public Car getById(Long id) {
        return null;
    }

    @Override
    public List<Car> getAll() {
        return List.of();
    }

    @Override
    public Car update(Car car) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
