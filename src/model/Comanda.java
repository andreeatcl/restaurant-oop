package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Comanda {
    private final int id;
    private LocalDateTime dataSiOra;
    private Map<Integer, Integer> produse = new HashMap<>();
    private double total;

    // constructor cu total necunoscut -> avem nevoie de meniu
    public Comanda(int id, LocalDateTime dataSiOra, Map<Integer, Integer> produse, Map<Integer, Produs> meniu) {
        this.id = id;
        this.dataSiOra = dataSiOra;
        this.produse = produse;
        this.total = calculeazaTotal(meniu);
    }

    // constructor cu total cunoscut (citit din fisier)
    public Comanda(int id, LocalDateTime dataSiOra, Map<Integer, Integer> produse, double total) {
        this.id = id;
        this.dataSiOra = dataSiOra;
        this.produse = produse;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataSiOra() {
        return dataSiOra;
    }

    public void setDataSiOra(LocalDateTime dataSiOra) {
        this.dataSiOra = dataSiOra;
    }

    public Map<Integer, Integer> getProduse() {
        return produse;
    }

    public void setProduse(Map<Integer, Integer> produse) {
        this.produse = produse;
    }

    public double getTotal() {
        return total;
    }

    public double calculeazaTotal(Map <Integer, Produs> meniu) {
        double suma = 0;
        for (int id : produse.keySet()) {
            // cantitate * pret
            suma += produse.get(id) * meniu.get(id).getPret();
        }
        return suma;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", dataSiOra=" + dataSiOra +
                ", produse=" + produse +
                ", total=" + total +
                '}';
    }
}
