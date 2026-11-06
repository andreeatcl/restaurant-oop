package service;

import exceptions.ProdusInexistentException;
import model.Comanda;
import model.Produs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Rapoarte {
    private final Map<Integer, Produs> meniu;
    private final List<Comanda> comenzi;

    private static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String[] ZILELE_SAPT = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica"};

    public Rapoarte(Map<Integer, Produs> meniu, List<Comanda> comenzi) {
        this.meniu = meniu;
        this.comenzi = comenzi;
    }

    // helpers
    private LocalDate citesteData(Scanner scanner, String tag) {
        System.out.print(tag + " (DD.MM.YYYY): ");
        return LocalDate.parse(scanner.nextLine().trim(), FORMAT_DATA);
    }

    private boolean nuExistaDate() {
        if (comenzi == null || comenzi.isEmpty()) {
            System.out.println("Nu exista comenzi inregistrate!");
            return true;
        }
        return false;
    }

    // returneaza comenzile dintr-o zi
    private List<Comanda> getComenziByZi(LocalDate data) {
        List<Comanda> rezultat = new ArrayList<>();
        for (Comanda c : comenzi) {
            if (c.getDataSiOra().toLocalDate().equals(data)) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    // returneaza comenzile dintr-o perioada
    private List<Comanda> getComenziByPerioada(LocalDate start, LocalDate sfarsit) {
        List<Comanda> rezultat = new ArrayList<>();
        for (Comanda c : comenzi) {
            LocalDate data = c.getDataSiOra().toLocalDate();
            if (!data.isBefore(start) && !data.isAfter(sfarsit)) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    // map produse si cantitatea vanduta
    private Map<Integer, Integer> cantitatiVandute() {
        Map<Integer, Integer> cantitati = new HashMap<>();
        for (Comanda c : comenzi) {
            for (Map.Entry<Integer, Integer> linie : c.getProduse().entrySet()) {
                cantitati.merge(linie.getKey(), linie.getValue(), Integer::sum);
            }
        }
        return cantitati;
    }

    private void header() {
        System.out.println("\n------------------------------------ RAPORT ------------------------------------");
    }

    private void footer() {
        System.out.println("\n--------------------------------------------------------------------------------");
    }

    // ==================================== RAPOARTE ====================================
    // 1. Total vanzari intr-o zi
    public void totalZi(Scanner scanner) {
        if (nuExistaDate()) return;
        LocalDate data = citesteData(scanner, "Data");

        List<Comanda> comenziZi = getComenziByZi(data);
        double total = 0;
        for (Comanda c : comenziZi) {
            total += c.getTotal();
        }

        header();
        System.out.println("TOTAL VANZARI IN ZIUA " + data.format(FORMAT_DATA) + "\n");
        System.out.printf("Numar comenzi : %d", comenziZi.size());
        System.out.printf("Total vanzari : %.2f RON", total);
        footer();
    }

    // 2. Numarul total de comenzi dintr-o anumita perioada
    public void nrComenziPerioada(Scanner scanner) {
        if (nuExistaDate()) return;
        LocalDate start = citesteData(scanner, "Data de inceput a perioadei");
        LocalDate sfarsit = citesteData(scanner, "Data de sfarsit a perioadei");

        int cnt = getComenziByPerioada(start, sfarsit).size();

        header();
        System.out.println("NUMAR COMENZI IN PERIOADA " + start.format(FORMAT_DATA) + " - " + sfarsit.format(FORMAT_DATA) + "\n");
        System.out.printf("Numar comenzi: %d", cnt);
        footer();
    }

    // 3. Produsele cele mai vandute (dupa cantitatea comandata)
    public void topProduse(Scanner scanner) {
        if (nuExistaDate()) return;

        System.out.print("Top cate produse? Introduceti nr: ");
        int top = Integer.parseInt(scanner.nextLine().trim());

        // transformam map-ul in lista pentru sortare
        List<Map.Entry<Integer, Integer>> sorted = new ArrayList<>(cantitatiVandute().entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        header();
        System.out.println("TOP " + top + " PRODUSE DUPA CANTITATEA VANDUTA");
        for (int i = 0; i < Math.min(top, sorted.size()); i++) {
            Produs p = meniu.get(sorted.get(i).getKey());
            System.out.printf("%d | %-32s | %2d portii\n", i + 1, p.getDenumire(), sorted.get(i).getValue());
        }
        footer();
    }

    // 4. Valoarea totala a vanzarilor pentru un anumit produs
    public void vanzariProdus(Scanner scanner) throws ProdusInexistentException {
        if (nuExistaDate()) return;

        System.out.print("ID produs: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Produs p = meniu.get(id);
        if (p == null) {
            throw new ProdusInexistentException("Produsul cu id-ul " + id + " nu exista in meniu!");
        }
        int cantitate = cantitatiVandute().getOrDefault(id, 0);

        header();
        System.out.println("VANZARI PENTRU " + p.getDenumire() + "\n");
        System.out.printf("Cantitate vanduta : %d portii\n", cantitate);
        System.out.printf("Pret unitar       : %.2f RON\n", p.getPret());
        System.out.printf("Valoare totala    : %.2f RON\n", cantitate * p.getPret());
        footer();
    }

    // 5. Comenzile realizate intr-o anumita zi
    public void comenziZi(Scanner scanner) {
        if (nuExistaDate()) return;

        LocalDate data = citesteData(scanner, "Data");
        List<Comanda> comenziZi = getComenziByZi(data);

        header();
        System.out.println("COMENZI DIN " + data.format(FORMAT_DATA) + "\n");
        if (comenziZi.isEmpty()) {
            System.out.println("Nu exista comenzi in aceasta zi!");
        }
        for (Comanda c : comenziZi) {
            System.out.println(c.afiseazaComandaDetaliata(meniu));
        }
        footer();
    }

    // 6. Valoarea medie a unei comenzi
    public void valoareMedie() {
        if (nuExistaDate()) return;

        double sum = 0;
        for (Comanda c : comenzi) {
            sum += c.getTotal();
        }

        header();
        System.out.println("VALOAREA MEDIE A UNEI COMENZI\n");
        System.out.printf("Numar comenzi : %d\n", comenzi.size());
        System.out.printf("Total vanzari : %.2f RON\n", sum);
        System.out.printf("Valoare medie : %.2f RON\n", sum / comenzi.size());
        footer();
    }

    // 7. Vanzari in functie de zilele saptamanii
    public void vanzariPeZile() {
        if (nuExistaDate()) return;

        double[] vanzari = new double[7];
        for (Comanda c : comenzi) {
            int zi = c.getDataSiOra().getDayOfWeek().getValue() - 1;
            vanzari[zi] += c.getTotal();
        }

        header();
        System.out.println("VANZARI PE ZILELE SAPTAMANII\n");
        for (int i = 0; i < 7; i++) {
            System.out.printf("%-10s | %10.2f RON\n", ZILELE_SAPT[i], vanzari[i]);
        }
        footer();
    }

    // 8. Numarul de comenzi in functie de ora
    public void oraDeVarf() {
        if (nuExistaDate()) return;

        int[] nrComenzi = new int[24];
        for (Comanda c : comenzi) {
            nrComenzi[c.getDataSiOra().getHour()]++;
        }

        int peak = 0;
        for (int ora = 1; ora < 24; ora++) {
            if (nrComenzi[ora] > nrComenzi[peak]) {
                peak = ora;
            }
        }

        header();
        System.out.println("NUMAR DE COMENZI PE ORE\n");
        for (int ora = 0; ora < 24; ora++) {
            if (nrComenzi[ora] > 0) {
                System.out.printf("%02d:00 - %02d:59 | %4d comenzi\n", ora, ora, nrComenzi[ora]);
            }
        }
        System.out.printf("\nOra de varf: %02d:00 - %02d:59 (%d comenzi)\n", peak, peak, nrComenzi[peak]);
        footer();
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
