package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("test")
                .createEntityManager();


        entityManager.getTransaction().begin();

        Patient patient = new Patient("Peter", "Petrov", "pp@yahoo.com", new Date(1975, 07, 9), true);
        Visitation visitation = new Visitation();
        visitation.setDateTime(LocalDateTime.now());
        visitation.setComments("The patient is in good condition");
        visitation.setPatient(patient);
        Diagnose cold = new Diagnose();
        cold.setName("cold");
        Medicament medicament = new Medicament();
        medicament.setName("Aspirin");
        patient.getDiagnoses().add(cold);
        patient.getMedicaments().add(medicament);
        patient.getVisitations().add(visitation);

        entityManager.persist(patient);
        entityManager.getTransaction().commit();
    }
}