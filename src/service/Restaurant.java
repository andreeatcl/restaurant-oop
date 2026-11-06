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

/**
 * Clasa care reprezinta restaurantul: retine meniul si comenzile si ofera
 * operatiile de administrare (adaugare produse, adaugare/modificare/stergere comenzi),
 * operatiile de afisare si rapoarte
 */
public class Restaurant {
    private final Map<Integer, Produs> meniu = new HashMap<>();
    private final List<Comanda> comenzi = new ArrayList<>();
    private final Rapoarte rapoarte = new Rapoarte(meniu, comenzi);

    public Map<Integer, Produs> getMeniu() {
        return meniu;
    }

    public List<Comanda> getComenzi() {
        return comenzi;
    }

    /**
     * @return obiectul care genereaza rapoartele pe baza meniului si comenzilor acestui restaurant
     */
    public Rapoarte getRapoarte() {
        return rapoarte;
    }

    /**
     *
     * @return produsele din meniu (valorile colectiei meniu)
     */
    public Collection<Produs> getProduse() {
        return this.meniu.values();
    }

    /**
     * adauga produsele date in meniu
     *
     * @param produse produsele citite din fisier
     */
    public void incarcaProduse(Collection<Produs> produse) {
        for (Produs p : produse) {
            meniu.put(p.getId(), p);
        }
    }

    /**
     * adauga comenzile date la lista de comenzi a restaurantului
     *
     * @param comenzi comenzile citite din fisier
     */
    public void incarcaComenzi(Collection<Comanda> comenzi) {
        this.comenzi.addAll(comenzi);
    }

    /**
     * @return urmatorul id liber pentru un produs
     */
    private int urmatorulIdProdus() {
        int vmax = 0;
        for (int id : meniu.keySet()) {
            if (id > vmax) vmax = id;
        }
        return vmax + 1;
    }

    /**
     * @return urmatorul id liber pentru o comanda
     */
    private int urmatorulIdComanda() {
        int vmax = 0;
        for (Comanda c : comenzi) {
            if (c.getId() > vmax) vmax = c.getId();
        }
        return vmax + 1;
    }

    /**
     * afiseaza meniul, produsele fiind grupate pe categorii
     */
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

    /**
     * afiseaza toate comenzile in format detaliat
     */
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

    // ========================================== METODE CRUD =========================================

    /**
     * Adauga un produs nou in meniu, citind datele de la tastatura
     *
     * @param scanner               sursa de intrare
     * @throws CampGolException daca un camp este gol
     * @throws DateInvalideException daca un camp are valori invalide
     */
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

    /**
     * adauga o comanda noua. utilizatorul introducere perechi de tip idProdus-cantitate
     * daca un produs e inclus deja in comanda, cantitatile se aduna
     *
     * @param scanner                       sursa de intrare
     * @throws ProdusInexistentException    daca un id introdus nu corespunde niciunui produs din meniu
     * @throws ComandaGoalaException        daca nu s-a introdus niciun produs
     * @throws DateInvalideException        daca o cantitate nu este pozitiva
     * @throws CampGolException             daca datele comenzii sunt incomplete
     */
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

    /**
     * Cauta o comanda dupa ID
     *
     * @param id    id-ul comenzii cautate
     * @return comanda gasita
     * @throws ProdusInexistentException daa nu exista nicio comanda cu acest id
     */
    private Comanda cautaComanda(int id) throws ProdusInexistentException {
        for (Comanda c : comenzi) {
            if (c.getId() == id) return c;
        }
        throw new ProdusInexistentException("Comanda cu id-ul " + id + " nu exista!");
    }

    /**
     * Sterge o comanda dupa un ID introdus de la tastatura
     *
     * @param scanner           sursa de intrare
     * @throws ProdusInexistentException    daca nu exista nicio comanda cu acest id
     */
    public void stergeComanda(Scanner scanner) throws ProdusInexistentException {
        afiseazaComenzi();
        System.out.print("ID-ul comenzii de sters: ");
        Comanda c = cautaComanda(Integer.parseInt(scanner.nextLine().trim()));
        comenzi.remove(c);
        System.out.println("Comanda " + c.getId() + " a fost stearsa cu succes!");
    }

    /**
     * modifica o comanda existenta: adauga un produs / schimba cantitatea unui produs deja comandat sau elimina un produs
     * modificarile se aplica pe o copie si comanda se actualizeaza doar daca trec validarile pt copie
     *
     * @param scanner                       sursa de intrare
     * @throws ProdusInexistentException    daca comanda/produsul nu exista
     * @throws DateInvalideException        daca optiunea/cantitatea sunt invalide
     * @throws CampGolException             daca datele comenzii sunt incomplete
     * @throws ComandaGoalaException        daca comanda ar ramane fara produse
     */
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

}
