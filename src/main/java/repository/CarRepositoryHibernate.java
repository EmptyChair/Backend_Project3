package repository;

import domain.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarRepositoryHibernate implements CarRepository {

    //needs to know all about java objects and DB lines to make them work together
    private EntityManager entityManager;

    public CarRepositoryHibernate() {
        entityManager = new Configuration()
                .configure("hibernate/postgres.cfg.xml")
                .buildSessionFactory()
                .createEntityManager();
    }

    @Override
    public Car save(Car car) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // begin transaction
            // ready to reverse things to this moment if transaction fails
            transaction.begin();
            entityManager.persist(car);
            //if successful, transaction is closed and new state of things is saved
            transaction.commit();
            return car;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: can't save the car");
        }
    }

    @Override
    public Car getById(Long id) {
        return entityManager.find(Car.class, id);
    }

    @Override
    public List<Car> getAll() {
        //TODO
        //create Criteria builder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //create criteria Query for the criteria builder
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);
        //create root entity
        Root<Car> root = cq.from(Car.class);
        //select all attributes
        cq.select(root);
        // turn query into a Query-type object and execute
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Car update(Car car) {
        EntityTransaction transaction = entityManager.getTransaction();
        //TODO
        try {
            transaction.begin();
            entityManager.merge(car);
            transaction.commit();
            return car;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: can't update the car");
        }
    }

    @Override
    public void delete(Long id) {
        //TODO
        Car carRemoved = entityManager.find(Car.class, id);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(carRemoved);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: can't remove the car");
        }
    }
}
