package service;

import exceptions.ProdusInexistentException;
import model.Comanda;
import model.Produs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Rapoarte {
    private final Map<Integer, Produs> meniu;
    private final List<Comanda> comenzi;

    public Rapoarte(Map<Integer, Produs> meniu, List<Comanda> comenzi) {
        this.meniu = meniu;
        this.comenzi = comenzi;
    }

    // helpers
    private LocalDate citesteData(Scanner scanner) {
        System.out.print("Data (DD.MM.YYYY): ");
        return LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private boolean nuExistaDate() {
        if (comenzi == null || comenzi.isEmpty()) {
            System.out.println("Nu exista comenzi inregistrate!");
            return true;
        }
        return false;
    }

    // ==================================== RAPOARTE ====================================
    // 1. Total vanzari intr-o zi
    public void totalZi(Scanner scanner) {

    }

    // 2. Numarul total de comenzi dintr-o anumita perioada
    public void nrComenziPerioada(Scanner scanner) {

    }

    // 3. Produsele cele mai vandute (dupa cantitatea comandata)
    public void topProduse() {

    }

    // 4. Valoarea totala a vanzarilor pentru un anumit produs
    public void vanzariProdus(Scanner scanner) throws ProdusInexistentException {

    }

    // 5. Comenzile realizate intr-o anumita zi
    public void comenziZi(Scanner scanner) {

    }

    // 6. Valoarea medie a unei comenzi
    public void valoareMedie() {

    }

    // 7. Vanzari in functie de zilele saptamanii
    public void vanzariPeZile() {
        double[] vanzari = new double[7];
    }

    // 8. Numarul de comenzi in functie de ora
    public void oraDeVarf() {
        int[] comenzi = new int[24];
    }

    // 9. Top produse vandute (in functie de venitul generat)
    public void bestsellers() {

    }

    // 10. Top produse vandute pe categorii
    public void vanzariPeCategorii() {

    }

    // 11. Cea mai mica si cea mai mare comanda
    public void comandaMinMaxTotal() {

    }

    // 12. Lista produselor nevandute
    public void produseNevandute() {

    }

    // 13. Distributia caloriilor pe intervale orare
    public void caloriiPeOra() {

    }

    // 14. Raport portii vegetariene
    public void raportVegetarian() {

    }
}
