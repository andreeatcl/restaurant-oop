package service;

import exceptions.CampGolException;
import exceptions.DateInvalideException;
import model.Categorie;
import model.Comanda;
import model.Produs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Clasa utilitara pentru citirea si salvarea produselor si comenzilor in fisiere JSON
 * Toate metodele sunt statice
 */
public class FisierUtils {

    /**
     * Citeste produsele dintr-un fisier JSON
     * Daca fisierul nu exista sau este gol, returneaza o lista goala
     *
     * @param caleFisier                    calea catre fisierul json
     * @return lista produselor citite
     * @throws IOException                  daca fisierul nu poate fi citit
     * @throws DateInvalideException        daca fisierul nu este un json valid sau un produs are valori invalide
     * @throws CampGolException             daca un produs are un camp gol
     */
    public static List<Produs> citesteProduse(String caleFisier) throws IOException, DateInvalideException, CampGolException {
        List<Produs> rezultat = new ArrayList<>();

        File fisier = new File(caleFisier);
        if (!fisier.exists() || fisier.length() == 0) {
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

    /**
     * Salveaza produsele intr-un fisier JSON
     *
     * @param caleFisier            calea catre fisierul json
     * @param produse               produsele de salvat
     * @throws IOException daca fisierul nu poate fi scris
     */
    public static void salveazaProduse(String caleFisier, Collection<Produs> produse) throws IOException {
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

    /**
     * Citeste comenzile dintr-un fisier JSON
     * Daca fisierul nu exista sau este gol, returneaza o lista goala
     *
     * @param caleFisier                calea catre fisierul json
     * @return lista comenzilor citite
     * @throws IOException              daca fisierul nu poate fi citit
     * @throws DateInvalideException    daca fisierul nu este un json valid sau un produs are valori invalide
     * @throws CampGolException         daca o comanda are un camp gol
     */
    public static List<Comanda> citesteComenzi(String caleFisier) throws IOException, DateInvalideException, CampGolException {
        List<Comanda> rezultat = new ArrayList<>();

        File fisier = new File(caleFisier);
        if (!fisier.exists() || fisier.length() == 0) {
            return rezultat;
        }

        try (FileInputStream file = new FileInputStream(fisier)) {
            JSONTokener tokener = new JSONTokener(file);
            JSONArray comenzi = new JSONArray(tokener);

            Set<Integer> idVazute = new HashSet<>();

            for (int i = 0 ; i < comenzi.length(); i++) {
                try {
                    JSONObject comanda = comenzi.getJSONObject(i);
                    Map<Integer, Integer> produse = new HashMap<>();
                    JSONArray jsonProduse = comanda.getJSONArray("produse");
                    for (int j = 0; j < jsonProduse.length(); j++) {

                        JSONObject jsonProdus = jsonProduse.getJSONObject(j);
                        int idProdus = jsonProdus.getInt("idProdus");

                        if (produse.containsKey(idProdus)) {
                            throw new DateInvalideException("Produsul " + idProdus + " apare de 2 ori in comanda " + comanda.getInt("id"));
                        }
                        produse.put(idProdus, jsonProdus.getInt("cantitate"));
                    }

                    Comanda c = new Comanda(
                            comanda.getInt("id"),
                            LocalDateTime.parse(comanda.getString("dataSiOra")),
                            produse,
                            comanda.getDouble("total")
                    );

                    if (!idVazute.add(c.getId())) {
                        throw new DateInvalideException("ID comanda " + c.getId() + " duplicat in " + caleFisier);
                    }

                    rezultat.add(c);
                }
                catch (JSONException | IllegalArgumentException | DateTimeParseException e) {
                    throw new DateInvalideException("Comanda invalida la pozitia " + i + " in " + caleFisier + ": " + e.getMessage());
                }
            }
        }
        catch (JSONException e) {
            throw new DateInvalideException("Fisierul JSON " + caleFisier + " nu este valid: " + e.getMessage());
        }

        return rezultat;
    }

    /**
     * salveaza comenzile intr-un fisier JSON
     *
     * @param caleFisier            calea catre fisierul json
     * @param comenzi               comenzile de salvat
     * @throws IOException          daca fisierul nu poate fi salvat
     */
    public static void salveazaComenzi(String caleFisier, List<Comanda> comenzi) throws IOException {
        JSONArray rezultat = new JSONArray();

        for (Comanda c : comenzi) {
            JSONObject obj = new JSONObject();

            obj.put("id", c.getId());
            obj.put("dataSiOra", c.getDataSiOra().toString());
            JSONArray produse = new JSONArray();
            for (int id : c.getProduse().keySet()) {
                JSONObject produs = new JSONObject();
                produs.put("idProdus", id);
                produs.put("cantitate", c.getProduse().get(id));
                produse.put(produs);
            }
            obj.put("produse", produse);
            obj.put("total", c.getTotal());

            rezultat.put(obj);
        }

        try (FileWriter writer = new FileWriter(caleFisier)) {
            writer.write(rezultat.toString(2));
        }
    }

}
