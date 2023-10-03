package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("test")
                .createEntityManager();

        entityManager.getTransaction().begin();
        Product product = new Product();
        product.setName("Shampoo");
        product.setPrice(BigDecimal.TEN);
        Sale sale = new Sale();
        sale.setDateTime(LocalDateTime.now());
        sale.setProduct(product);
        product.getSales().add(sale);

        entityManager.persist(product);

        entityManager.getTransaction().commit();
    }
}