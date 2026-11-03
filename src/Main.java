import model.Categorie;
import model.Comanda;
import model.Produs;
import service.FisierUtils;
import service.Restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        List<Produs> produse = new ArrayList<>();
        List<Comanda> comenzi = new ArrayList<>();

        try {
            Restaurant restaurant = new Restaurant();

            // testare citire & scriere in json - PRODUSE
            produse.add(new Produs(1, "Bruschete", Categorie.APERITIVE, 20.5, 350, true));
            produse.add(new Produs(2, "Sprite 330ml", Categorie.BAUTURI, 13.0, 130, true));

            FisierUtils.salveazaProduse("data/produse.json", produse);

            produse.clear();
            produse = FisierUtils.citesteProduse("data/produse.json");
            System.out.println(produse);

            for (Produs p : produse) {
                restaurant.getMeniu().put(p.getId(), p);
            }

            // testare citire & scriere in json - COMENZI
            Map<Integer, Integer> linii = new HashMap<>();
            linii.put(1, 2);
            linii.put(2, 3);
            comenzi.add(new Comanda(1, LocalDateTime.now(), linii, restaurant.getMeniu()));

            FisierUtils.salveazaComenzi("data/comenzi.json", comenzi);
            comenzi.clear();
            comenzi = FisierUtils.citesteComenzi("data/comenzi.json");
            System.out.println(comenzi);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}