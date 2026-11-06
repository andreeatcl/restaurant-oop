package model;

import exceptions.CampGolException;
import exceptions.DateInvalideException;
import exceptions.ProdusInexistentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * reprezinta o comanda plasata la restaurant
 * id, data si ora, produsele comandate (id, cantitate) si totalul de plata
 */
public class Comanda {
    private final int id;
    private LocalDateTime dataSiOra;
    private Map<Integer, Integer> produse = new HashMap<>();
    private double total;

    /**
     * Creeaza o comanda noua, calculand totalul pe baza preturilor din meniu
     *
     * @param id        id-ul comenzii - pozitiv
     * @param dataSiOra data si ora plasarii - nu poate fi in viitor
     * @param produse   produsele comandate, sub forma (key: id produs, value: cantitate)
     * @param meniu     meniul folosit pentru calculul totalului
     * @throws CampGolException          daca data si ora sau lista de produse sunt nule/goale
     * @throws DateInvalideException     daca id-ul nu este pozitiv sau data este in viitor
     * @throws ProdusInexistentException daca un produs din comanda nu exista in meniu
     */
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

    /**
     * creeaza o comanda cu totalul deja cunoscut (pt comenzi citite din fisier)
     *
     * @param id        id-ul comenzii - pozitiv
     * @param dataSiOra data si ora plasarii - nu poate fi in viitor
     * @param produse   produsele comandate, sub forma (key: id produs, value: cantitate)
     * @param total     totalul de plata (> 0)
     * @throws DateInvalideException     daca id-ul nu este pozitiv sau data este in viitor sau totalul nu e pozitiv
     * @throws CampGolException          daca data si ora sau lista de produse sunt nule/goale
     */
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

    /**
     * Seteaza totalul comenzii
     *
     * @param meniu       meniul din care se iau preturile
     * @return  totalul calculat (RON)
     * @throws ProdusInexistentException daca un produs din comanda nu exista in meniu
     */
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

    /**
     * construieste o reprezentare detaliata a comenzii
     *
     * @param meniu         meniul din care se iau denumirile si preturile
     * @return textul formatat
     */
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
