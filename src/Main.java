import exceptions.ProdusInexistentException;
import service.FisierUtils;
import service.Rapoarte;
import service.Restaurant;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();
        Scanner scanner = new Scanner(System.in);

        try {
            restaurant.incarcaProduse(FisierUtils.citesteProduse("data/produse.json"));
            restaurant.incarcaComenzi(FisierUtils.citesteComenzi("data/comenzi.json"));
        } catch (Exception e) {
            System.out.println("Eroare la citirea din fisiere: " + e.getMessage());
            return;
        }

        boolean isRunning = true;
        while (isRunning) {
            afiseazaMeniuOptiuni();
            System.out.print("Introdu optiunea: ");
            String optiune = scanner.nextLine().trim();

            try {
                switch (optiune) {
                    case "1" -> restaurant.afiseazaMeniu();
                    case "2" -> restaurant.adaugaProdus(scanner);
                    case "3" -> restaurant.afiseazaComenzi();
                    case "4" -> restaurant.adaugaComanda(scanner);
                    case "5" -> restaurant.modificaComanda(scanner);
                    case "6" -> restaurant.stergeComanda(scanner);
                    case "7" -> meniuRapoarte(restaurant, scanner);
                    case "8" -> salveaza(restaurant);
                    case "0" -> isRunning = false;
                    default -> System.out.println("Optiune invalida. Incearca din nou!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valoarea introdusa nu e un numar!");
            } catch (DateTimeParseException e) {
                System.out.println("Data introdusa nu e valida!");
            } catch (Exception e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }

        salveaza(restaurant);
        System.out.println("\nVa multumim ca ati folosit aplicatia noastra!");
        scanner.close();
    }

    private static void afiseazaMeniuOptiuni() {
        System.out.println("\n===================================== RESTAURANT =====================================");
        System.out.println("1. Afiseaza meniul restaurantului");
        System.out.println("2. Adauga un produs in meniu");
        System.out.println("3. Afiseaza toate comenzile");
        System.out.println("4. Inregistreaza o comanda");
        System.out.println("5. Modifica o comanda");
        System.out.println("6. Sterge o comanda");
        System.out.println("7. Rapoarte");
        System.out.println("0. Exit");
    }

    private static void salveaza(Restaurant restaurant) {
        try {
            FisierUtils.salveazaProduse("data/produse.json", restaurant.getProduse());
            FisierUtils.salveazaComenzi("data/comenzi.json", restaurant.getComenzi());
            System.out.println("Datele au fost salvate cu succes!");
        } catch (IOException e) {
            System.out.println("Eroare la salvare: " + e.getMessage());
        }
    }

    private static void meniuRapoarte(Restaurant restaurant, Scanner scanner) throws ProdusInexistentException {
        Rapoarte rapoarte = restaurant.getRapoarte();
        boolean back = false;
        boolean showMenu = true;
        while (!back) {
            if (showMenu) {
                System.out.println("\n------------------------------------ RAPOARTE ------------------------------------");
                System.out.println("1. Valoarea totala a comenzilor dintr-o anumita zi");
                System.out.println("2. Numarul total de comenzi dintr-o anumita perioada");
                System.out.println("3. Top produse vandute (in functie de cantitate)");
                System.out.println("4. Venitul total generat de un anumit produs");
                System.out.println("5. Lista comenzilor realizate intr-o anumita zi");
                System.out.println("6. Valoarea medie a unei comenzi");
                System.out.println("7. Statistici vanzari pe zilele saptamanii");
                System.out.println("8. Distributia comenzilor in functie de ora");
                System.out.println("9. Top produse vandute (in functie de venitul generat)");
                System.out.println("10. Top produse vandute pe categorii");
                System.out.println("11. Venit pe intervale orare");
                System.out.println("12. Lista produselor nevandute");
                System.out.println("13. Distributia caloriilor pe intervale orare");
                System.out.println("14. Vanzari pe zilele saptamanii si pe ora");
                System.out.println("0. Inapoi la meniul principal");
            }
            showMenu = false;
            System.out.print("Introdu optiunea (m = meniu, 0 = inapoi): ");
            String optiune = scanner.nextLine().trim();

            switch (optiune) {
                case "m" -> showMenu = true;
                case "1" -> rapoarte.totalZi(scanner);
                case "2" -> rapoarte.nrComenziPerioada(scanner);
                case "3" -> rapoarte.topProduse(scanner);
                case "4" -> rapoarte.vanzariProdus(scanner);
                case "5" -> rapoarte.comenziZi(scanner);
                case "6" -> rapoarte.valoareMedie();
                case "7" -> rapoarte.vanzariPeZile();
                case "8" -> rapoarte.oraDeVarf();
                case "9" -> rapoarte.bestsellers(scanner);
                case "10" -> rapoarte.vanzariPeCategorii(scanner);
                case "11" -> rapoarte.venitPeOra();
                case "12" -> rapoarte.produseNevandute();
                case "13" -> rapoarte.caloriiPeOra();
                case "14" -> rapoarte.vanzariZiOra();
                case "0" -> back = true;
                default -> System.out.println("Optiune invalida. Incearca din nou!");
            }
        }
    }
}