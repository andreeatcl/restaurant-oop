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
            // restaurant.getMeniu().put(1, new Produs(1, "Bruschete", Categorie.APERITIVE, 20.5, 350, true));
            // restaurant.getMeniu().put(2, new Produs(2, "Sprite 330ml", Categorie.BAUTURI, 13.0, 130, true));
            // restaurant.getMeniu().put(3, new Produs(3, "", Categorie.BAUTURI, 0, 130, true));
            produse.add(new Produs(1, "Bruschete", Categorie.APERITIVE, 20.5, 350, true));
            produse.add(new Produs(2, "Sprite 330ml", Categorie.BAUTURI, 13.0, 130, true));

            FisierUtils.salveazaProduse("produse.json", produse);

            produse.clear();
            produse = FisierUtils.citesteProduse("produse.json");
            System.out.println(produse);

            // Map<Integer, Integer> linii = new HashMap<>();
            // linii.put(1, 2);
            // linii.put(2, 3);

            // Comanda comanda = new Comanda(1, LocalDateTime.now(), linii, restaurant.getMeniu());
            // System.out.println(comanda.getTotal());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}