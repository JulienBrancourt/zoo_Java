package org.example;

import org.example.Entity.Animal;
import org.example.Entity.RegimeAlimentaire;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class IHM {
    private static Scanner scanner;

    public IHM() {
        scanner = new Scanner(System.in);
    }

    public void start () {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo_jpa");
        EntityManager em = emf.createEntityManager();

        String choix;

        while(true){
            System.out.println(" ---- Zoo -----");
            System.out.println("1/ creation d'animal");
            System.out.println("2/ recherche par ID");
            System.out.println("3/ recherche par nom");
            System.out.println("4/ recherche par régime alimentaire");
            choix = scanner.nextLine();
            switch(choix){
                case "1"->addAnimal(em);
                case "2"->animalParId(em) ;
                case "3"->animalParNom(em);
                case "4"->animalParRegime(em) ;
                default -> {
                    return;
                }
            }
        }
    }

    private void addAnimal (EntityManager em) {
        em.getTransaction().begin();
        Animal animal = new Animal();
        System.out.println("Saisissez le nom : ");
        String nom = scanner.nextLine();

        System.out.println("Saisissez l'âge : ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Saisissez la date d'arrivée (dd/mm/yyyy): ");
        String dateString = scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateArrivee;
        try {
            dateArrivee = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Régime alimentaire :");
        System.out.println("1/ HERBIVORE ?");
        System.out.println("2/ CARNIVORE ?");
        System.out.println("3/ OMNIVORE ?");
        String choixRegime = scanner.nextLine();

        RegimeAlimentaire choixRegimeAlimentaire;
        switch(choixRegime){
            case "1" -> choixRegimeAlimentaire = RegimeAlimentaire.HERBIVORE;
            case "2" -> choixRegimeAlimentaire = RegimeAlimentaire.CARNIVORE;
            case "3" -> choixRegimeAlimentaire = RegimeAlimentaire.OMNIVORE;
            default -> choixRegimeAlimentaire = RegimeAlimentaire.OMNIVORE;
        }

        animal.setName(nom);
        animal.setAge(age);
        animal.setRegimeAlimentaire(choixRegimeAlimentaire);
        animal.setDateArrivee(dateArrivee);

        em.persist(animal);
        em.getTransaction().commit();

    }

    private void animalParId (EntityManager em) {
        System.out.println("Quel id ?");

        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal animal = em.find(Animal.class, id);
            System.out.println(animal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void animalParNom (EntityManager em) {

        System.out.println("Quel nom ?");
        String nom = scanner.nextLine();
        TypedQuery<Animal> query = em.createQuery("select a from Animal a where a.name like :name", Animal.class);
        query.setParameter("name", nom);
        List<Animal> animals = query.getResultList();
        System.out.println(animals);
    }

    private void animalParRegime (EntityManager em) {
        System.out.println("Quel regime ?");
        System.out.println("1/ HERBIVORE ?");
        System.out.println("2/ CARNIVORE ?");
        System.out.println("3/ OMNIVORE ?");
        String choixRegime = scanner.nextLine();
        RegimeAlimentaire choixRegimeAlimentaire;
        switch(choixRegime){
            case "1" -> choixRegimeAlimentaire = RegimeAlimentaire.HERBIVORE;
            case "2" -> choixRegimeAlimentaire = RegimeAlimentaire.CARNIVORE;
            case "3" -> choixRegimeAlimentaire = RegimeAlimentaire.OMNIVORE;
            default -> choixRegimeAlimentaire = RegimeAlimentaire.OMNIVORE;

        }

        Query query = em.createQuery("select a from Animal a where a.regimeAlimentaire=:regimeAlimentaire");
        query.setParameter("regimeAlimentaire", choixRegimeAlimentaire);
        List<Animal> animals = query.getResultList();
        System.out.println(animals);

    }
}
