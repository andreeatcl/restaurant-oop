import exceptions.CampGolException;
import exceptions.DateInvalideException;
import model.Categorie;
import model.Comanda;
import model.Produs;
import service.FisierUtils;
import service.Restaurant;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();

        try {
            restaurant.incarcaProduse(FisierUtils.citesteProduse("data/produse.json"));
            restaurant.incarcaComenzi(FisierUtils.citesteComenzi("data/comenzi.json"));

            System.out.println("Produsele si comenzile au fost incarcate cu succes.");
        } catch (Exception e) {
            System.out.println("Eroare la citirea din fisiere: " + e.getMessage());
            return;
        }
        System.out.println("Produse: " + restaurant.getProduse());
        System.out.println("Comenzi: " + restaurant.getComenzi());

        try {
            Scanner scanner = new Scanner(System.in);
            // restaurant.adaugaProdus(scanner);
            restaurant.afiseazaMeniu();
            //restaurant.adaugaComanda(scanner);
        } catch (Exception e) {
            System.out.println("Eroare la adaugare: " + e.getMessage());
        }

        try {
            FisierUtils.salveazaProduse("data/produse.json", restaurant.getMeniu().values());
            FisierUtils.salveazaComenzi("data/comenzi.json", restaurant.getComenzi());
            System.out.println("Produsele si comenzile au fost salvate in fisiere cu succes.");
        } catch (IOException e) {
            System.out.println("Eroare la salvarea in fisiere: " + e.getMessage());
        }
    }
}