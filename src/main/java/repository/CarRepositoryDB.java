package repository;

import constants.Constants;
import domain.Car;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
        //opening connection via getConnection
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){
            //forming a query to be used in the PostGres database
            String query = String.format(
                    "INSERT INTO car (brand, price, year) VALUES ('%s',%s,%d);",
                    car.getBrand(), car.getPrice(),car.getYear());
            //statement is made out of connection
            Statement statement = connection.createStatement();
            //putting data - execute (used to put data inside the DB
            // as opposed to execute Query which is used to get the data out of DB)
            // demands the car not only to be saved to DB, but also retrieve the generated ID
            // to be retrieved from the DB
            // generated ID will be saved in the STatement object
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            //how to retrieve id?
            // declare resultSet and take generated id from statement
            // (as well as the entire dataset of the table)
            ResultSet resultSet = statement.getGeneratedKeys();
            // switching to the first line of the result set (first car)
            // next returns true/false
            resultSet.next();
            // Reading id: get Long value of the first column
            Long id = resultSet.getLong(1);
            // set
            car.setId(id);

            return car;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car getById(Long id) {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){
            String query = String.format("SELECT * FROM car WHERE id = %d", id);
            Statement statement = connection.createStatement();
            //want to retrieve - execute query
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            //use column names, not numbers, the sequence may change!
            String brand = resultSet.getString("brand");
            BigDecimal price = resultSet.getBigDecimal("price");
            int year = resultSet.getInt("year");
            return new Car(id, brand, price, year);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAll() {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){
            //TODO
            //continue until next() returns false
            List<Car> allCars = new ArrayList<>();
            String query = String.format("SELECT * FROM car");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String brand = resultSet.getString("brand");
                BigDecimal price = resultSet.getBigDecimal("price");
                int year = resultSet.getInt("year");
                allCars.add(new Car(id,brand, price, year));
            }
            return allCars;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car update(Car car) {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){
            //TODO price only
            BigDecimal price = car.getPrice();
            Long id = car.getId();
            Car updatedCar = getById(id);
            updatedCar.setPrice(price);
            String query = String.format("UPDATE car SET price = %s WHERE id = %d", price, id);
            Statement statement = connection.createStatement();
            statement.execute(query);
            return updatedCar;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        //block try with resources closes connection automatically
        try (Connection connection = getConnection()){
            //TODO
            String query = String.format("DELETE FROM car WHERE id = %d", id);
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
