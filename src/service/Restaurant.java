package service;

import model.Comanda;
import model.Produs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private Map<Integer, Produs> meniu = new HashMap<>();
    private List<Comanda> comenzi = new ArrayList<>();

    public Map<Integer, Produs> getMeniu() {
        return meniu;
    }

    public List<Comanda> getComenzi() {
        return comenzi;
    }

    // metode crud


    // metode rapoarte
}
