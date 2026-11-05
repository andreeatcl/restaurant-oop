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
    private final Rapoarte rapoarte = new Rapoarte(meniu, comenzi);

    public Map<Integer, Produs> getMeniu() {
        return meniu;
    }

    public List<Comanda> getComenzi() {
        return comenzi;
    }

    public Rapoarte getRapoarte() {
        return rapoarte;
    }

    public Collection<Produs> getProduse() {
        return this.meniu.values();
    }

    // metode incarcare din fisier
    public void incarcaProduse(Collection<Produs> produse) {
        for (Produs p : produse) {
            meniu.put(p.getId(), p);
        }
    }

    public void incarcaComenzi(Collection<Comanda> comenzi) {
        this.comenzi.addAll(comenzi);
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

    // afisare meniu
    public void afiseazaMeniu() {
        System.out.println("====================================== MENIU ======================================");
        for (Categorie categ : Categorie.values()) {
            System.out.println("\n--------------------------------- " + categ + " ---------------------------------");
            for (Produs p : meniu.values()) {
                if (p.getCategorie() == categ) {
                    System.out.println(p);
                }
            }
        }
    }

    // afisare comenzi
    public void afiseazaComenzi() {
        System.out.println("====================================== COMENZI ======================================");
        if (comenzi.isEmpty()) {
            System.out.println("Nu exista comenzi!");
            return;
        }
        for (Comanda c : comenzi) {
            System.out.println(c.afiseazaComandaDetaliata(meniu));
            System.out.println();
        }
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
        System.out.println("--- Introduceti produsele comandate ---");

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

    private Comanda cautaComanda(int id) throws ProdusInexistentException {
        for (Comanda c : comenzi) {
            if (c.getId() == id) return c;
        }
        throw new ProdusInexistentException("Comanda cu id-ul " + id + " nu exista!");
    }

    public void stergeComanda(Scanner scanner) throws ProdusInexistentException {
        afiseazaComenzi();
        System.out.print("ID-ul comenzii de sters: ");
        Comanda c = cautaComanda(Integer.parseInt(scanner.nextLine().trim()));
        comenzi.remove(c);
        System.out.println("Comanda " + c.getId() + " a fost stearsa cu succes!");
    }

    public void modificaComanda(Scanner scanner) throws ProdusInexistentException, DateInvalideException, CampGolException, ComandaGoalaException {
        afiseazaComenzi();
        System.out.print("ID-ul comenzii de modificat: ");
        Comanda c = cautaComanda(Integer.parseInt(scanner.nextLine().trim()));

        Map<Integer, Integer> copy = new HashMap<>(c.getProduse());

        System.out.println("1. Adauga un produs in comanda sau modifica cantitatea");
        System.out.println("2. Elimina un produs din comanda");
        System.out.print("Optiune: ");
        String choice = scanner.nextLine().trim();

        afiseazaMeniu();
        System.out.print("ID produs: ");
        int idProdus = Integer.parseInt(scanner.nextLine().trim());

        if (choice.equals("1")) {
            if (!meniu.containsKey(idProdus)) {
                throw new ProdusInexistentException("Produsul cu id-ul " + idProdus + " nu exista in meniu!");
            }
            System.out.print("Cantitate noua: ");
            int cantitate = Integer.parseInt(scanner.nextLine().trim());
            if (cantitate <= 0) {
                throw new DateInvalideException("Cantitatea trebuie sa fie pozitiva");
            }
            // suprascriem cantitatea existenta sau adaugam un produs nou
            copy.put(idProdus, cantitate);
        } else if (choice.equals("2")) {
            if (copy.remove(idProdus) == null) {
                throw new ProdusInexistentException("Produsul cu id-ul " + idProdus + " nu se afla in comanda!");
            }
        } else {
            throw new DateInvalideException("Optiune invalida.");
        }

        if (copy.isEmpty()) {
            throw new ComandaGoalaException("Comanda nu poate ramane fara produse! Va rugam sa stergeti comanda.");
        }

        c.setProduse(copy);
        c.calculeazaTotal(meniu);
        System.out.println("Comanda modificata cu succes!");
        System.out.println("Total nou: " + c.getTotal());

    }

    // metode rapoarte
}
