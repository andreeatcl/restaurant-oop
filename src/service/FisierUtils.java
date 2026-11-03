package service;

import exceptions.CampGolException;
import exceptions.DateInvalideException;
import model.Categorie;
import model.Produs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FisierUtils {
    public static List<Produs> citesteProduse(String caleFisier) throws IOException, DateInvalideException, CampGolException {
        List<Produs> rezultat = new ArrayList<>();

        File fisier = new File(caleFisier);
        if (!fisier.exists()) {
            return rezultat;
        }

        try (FileInputStream file = new FileInputStream(fisier)) {
            JSONTokener tokener = new JSONTokener(file);
            JSONArray produse = new JSONArray(tokener);

            for (int i = 0 ; i < produse.length(); i++) {
                try {
                    JSONObject produs = produse.getJSONObject(i);

                    Produs p = new Produs(
                            produs.getInt("id"),
                            produs.getString("denumire"),
                            Categorie.valueOf(produs.getString("categorie")),
                            produs.getDouble("pret"),
                            produs.getInt("calorii"),
                            produs.getBoolean("esteVegetarian")
                    );

                    rezultat.add(p);
                }
                catch (JSONException | IllegalArgumentException e) {
                    throw new DateInvalideException("Produs invalid la pozitia " + i + " in " + caleFisier + ": " + e.getMessage());
                }
            }
        }
        catch (JSONException e) {
            throw new DateInvalideException("Fisierul JSON " + caleFisier + " nu este valid: " + e.getMessage());
        }

        return rezultat;
    }

    public static void salveazaProduse(String caleFisier, List<Produs> produse) throws IOException {
        JSONArray rezultat = new JSONArray();

        for (Produs p : produse) {
            JSONObject obj = new JSONObject();

            obj.put("id", p.getId());
            obj.put("denumire", p.getDenumire());
            obj.put("categorie", p.getCategorie().name());
            obj.put("pret", p.getPret());
            obj.put("calorii", p.getCalorii());
            obj.put("esteVegetarian", p.isEsteVegetarian());

            rezultat.put(obj);
        }

        try (FileWriter writer = new FileWriter(caleFisier)) {
            writer.write(rezultat.toString(2));
        }
    }


}
