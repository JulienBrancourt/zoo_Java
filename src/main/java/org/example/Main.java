package org.example;

import org.example.Entity.Animal;
import org.example.Entity.RegimeAlimentaire;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {





        //recherche par regime alimentaire

//        Query query = em.createQuery("select a from Animal a where a.regimeAlimentaire=:regimeAlimentaire");
//        query.setParameter("regimeAlimentaire", RegimeAlimentaire.CARNIVORE);
//        List<Animal> animals = query.getResultList();
//        System.out.println(animals);

        IHM ihm = new IHM();

        ihm.start();







    }
}