package model;

import exceptions.CampGolException;
import exceptions.DateInvalideException;
import exceptions.ProdusInexistentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Comanda {
    private final int id;
    private LocalDateTime dataSiOra;
    private Map<Integer, Integer> produse = new HashMap<>();
    private double total;

    // constructor cu total necunoscut -> avem nevoie de meniu
    public Comanda(int id, LocalDateTime dataSiOra, Map<Integer, Integer> produse, Map<Integer, Produs> meniu) throws CampGolException, DateInvalideException, ProdusInexistentException {
        if (id <= 0) {
            throw new DateInvalideException("ID-ul comenzii trebuie sa aiba o valoare pozitiva!");
        }
        this.id = id;

        setDataSiOra(dataSiOra);
        if (produse != null) {
            setProduse(produse);
        }
        this.total = calculeazaTotal(meniu);
    }

    // constructor cu total cunoscut (citit din fisier)
    public Comanda(int id, LocalDateTime dataSiOra, Map<Integer, Integer> produse, double total) throws DateInvalideException, CampGolException {
        if (id <= 0) {
            throw new DateInvalideException("ID-ul comenzii trebuie sa aiba o valoare pozitiva!");
        }
        this.id = id;

        setDataSiOra(dataSiOra);
        setProduse(produse);
        setTotal(total);
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataSiOra() {
        return dataSiOra;
    }

    public void setDataSiOra(LocalDateTime dataSiOra) throws CampGolException, DateInvalideException {
        if (dataSiOra == null) {
            throw new CampGolException("Data si ora comenzii nu pot fi nule!");
        }
        if (dataSiOra.isAfter(LocalDateTime.now())) {
            throw new DateInvalideException("Data si ora comenzii nu pot fi in viitor!");
        }
        this.dataSiOra = dataSiOra;
    }

    public Map<Integer, Integer> getProduse() {
        return produse;
    }

    public void setProduse(Map<Integer, Integer> produse) throws CampGolException {
        if (produse == null || produse.isEmpty()) {
            throw new CampGolException("Lista de produse din comanda nu poate fi goala!");
        }
        this.produse = produse;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) throws DateInvalideException {
        if (total <= 0) {
            throw new DateInvalideException("Totalul comenzii trebuie sa fie mai mare decat zero!");
        }
        this.total = total;
    }

    public double calculeazaTotal(Map <Integer, Produs> meniu) throws ProdusInexistentException {
        double suma = 0;
        for (int id : produse.keySet()) {
            Produs produs = meniu.get(id);

            if (produs == null) {
                throw new ProdusInexistentException("Produsul cu ID: " + id + " nu exista!");
            }

            // cantitate * pret
            suma += produse.get(id) * produs.getPret();
        }
        return suma;
    }

    public String afiseazaComandaDetaliata(Map<Integer, Produs> meniu) {
        StringBuilder sb = new StringBuilder();
        sb.append("======================================================================\n");
        sb.append(String.format("Comanda %d | %s\n", id, dataSiOra.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
        for (var linie : produse.entrySet()) {
            Produs p = meniu.get(linie.getKey());
            sb.append(String.format("   %-4d | %-32s | %2d x %6.2f RON\n",
                    linie.getKey(), p.getDenumire(), linie.getValue(), p.getPret()));
        }
        sb.append("----------------------------------------------------------------------\n");
        sb.append(String.format("   Total: %.2f RON\n", total));
        sb.append("======================================================================\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Comanda %d | %s | %d produse | Total: %.2f RON",
                id, dataSiOra.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), produse.size(), total);
    }
}
