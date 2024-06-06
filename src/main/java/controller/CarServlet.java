package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Car;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.CarRepository;
import repository.CarRepositoryDB;
import repository.CarRepositoryMap;
import service.CarService;
import service.CarServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarServlet extends HttpServlet {

    //private CarService service = new CarServiceImpl(new CarRepositoryMap());
    private CarService service = new CarServiceImpl(new CarRepositoryDB());
    // receiving car or all cars
    // call myself, through default tomcat port 8080:
    // GET http://localhost:8080/cars?id=3 - one car
    // GET http://localhost:8080/cars - all cars
    // 127.0.0.1

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        // req - info sent by client
        // resp - what we send to client
        Map<String,String[]> params = req.getParameterMap();
        // id :[3] - everything is String
        if(params.isEmpty()){
            List<Car> allCars = service.getAll();
            resp.getWriter().write(allCars.toString());
        } else {
            Long id = Long.parseLong(params.get("id")[0]);
            Car car = service.getById(id);
            resp.getWriter().write(car.toString());
        }
    }


    //Saving car in database
    // POST http://localhost:8080/cars - car object will arrive in request

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(req.getReader(), Car.class);
        service.save(car);
        resp.getWriter().write(car.toString());
    }


    // Changing existing car in database:
    // PUT  http://localhost:8080/cars - car object to be changed will arrive in request

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(req.getReader(), Car.class);
        service.update(car);
        resp.getWriter().write(car.toString());
    }


    // Deleting car from DB
    // DELETE http://localhost:8080/cars?id=3


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doDelete(req, resp);
        //ObjectMapper mapper = new ObjectMapper();
       // Car car = mapper.readValue(req.getReader(), Car.class);
        //service.delete(req.getReader());
        //resp.getWriter().write(car.toString());
        Map<String,String[]> params = req.getParameterMap();
        // id :[3] - everything is String
        Long id = Long.parseLong(params.get("id")[0]);
        service.delete(id);
    }
}
