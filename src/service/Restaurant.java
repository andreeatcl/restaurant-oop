package service;

import exceptions.CampGolException;
import exceptions.ComandaGoalaException;
import exceptions.DateInvalideException;
import exceptions.ProdusInexistentException;
import model.Categorie;
import model.Comanda;
import model.Produs;

import java.time.LocalDateTime;
import java.util.*;

public class Restaurant {
    private Map<Integer, Produs> meniu = new HashMap<>();
    private List<Comanda> comenzi = new ArrayList<>();

    public Map<Integer, Produs> getMeniu() {
        return meniu;
    }

    public List<Comanda> getComenzi() {
        return comenzi;
    }

    public Collection<Produs> getProduse() {
        return this.meniu.values();
    }

    // metode contori id produse si comenzi
    private int urmatorulIdProdus() {
        int vmax = 0;
        for (int id : meniu.keySet()) {
            if (id > vmax) vmax = id;
        }
        return vmax + 1;
    }

    private int urmatorulIdComanda() {
        int vmax = 0;
        for (Comanda c : comenzi) {
            if (c.getId() > vmax) vmax = c.getId();
        }
        return vmax + 1;
    }

    // metode crud
    public void adaugaProdus(Scanner scanner) throws CampGolException, DateInvalideException {
        System.out.println("===================== ADAUGA UN PRODUS NOU IN MENIU =====================\n");
        System.out.print("Denumire: ");
        String denumire = scanner.nextLine();

        System.out.println("Pentru categorie, va rugam sa selectati una dintre urmatoarele: APERITIVE, FELURI_PRINCIPALE, DESERTURI, BAUTURI");
        System.out.print("Categorie: ");
        Categorie categorie = Categorie.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Pret: ");
        double pret = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Numar calorii: ");
        int calorii = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Este vegetarian? (true/false): ");
        boolean esteVegetarian = Boolean.parseBoolean(scanner.nextLine().trim());

        Produs p = new Produs(urmatorulIdProdus(), denumire, categorie, pret, calorii, esteVegetarian);
        meniu.put(p.getId(), p);
    }

    public void adaugaComanda(Scanner scanner) throws ProdusInexistentException, ComandaGoalaException, DateInvalideException, CampGolException {
        System.out.println("===================== ADAUGA O COMANDA NOUA =====================\n");

        Map<Integer, Integer> produseComandate = new HashMap<>();
        System.out.println("Introduceti produsele comandate");

        while (true) {
            System.out.println("\n======== Introducere produs ========");
            System.out.println("Daca doriti sa incheiati comanda, apasati ENTER");
            System.out.print("ID produs: ");
            String linie = scanner.nextLine().trim();
            if (linie.isEmpty()) {
                break;
            }
            int idProdus = Integer.parseInt(linie);

            if (!meniu.containsKey(idProdus)) {
                throw new ProdusInexistentException("Produsul cu id " + idProdus + " nu exista in meniu!");
            }

            System.out.print("Cantitate: ");
            int cantitate = Integer.parseInt(scanner.nextLine().trim());
            if (cantitate <= 0) {
                throw new DateInvalideException("Cantitatea trebuie sa fie pozitiva");
            }
            produseComandate.merge(idProdus, cantitate, Integer::sum);
        }

        if (produseComandate.isEmpty()) {
            throw new ComandaGoalaException("Comanda nu contine niciun produs!");
        }

        Comanda c = new Comanda(urmatorulIdComanda(), LocalDateTime.now(), produseComandate, meniu);
        comenzi.add(c);
        System.out.println("Comanda a fost inregistrata cu succes! Total de plata: " + c.getTotal());
    }

    // metode rapoarte
}
