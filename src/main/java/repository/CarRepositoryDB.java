package repository;

import constants.Constants;
import domain.Car;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){
            String query = String.format(
                    "INSERT INTO car (brand, price, year) VALUES ('%s',%s,%d);",
                    car.getBrand(), car.getPrice(),car.getYear());
            Statement statement = connection.createStatement();
            statement.execute(query);

            return car;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car getById(Long id) {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public List<Car> getAll() {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){

        } catch (Exception e) {

        }
        return List.of();
    }

    @Override
    public Car update(Car car) {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public void delete(Long id) {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){

        } catch (Exception e) {

        }

    }
}
