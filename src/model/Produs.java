package model;

import exceptions.CampGolException;
import exceptions.DateInvalideException;

/**
 * Reprezinta un produs din meniul restaurantului
 */
public class Produs {
    private final int id;
    private String denumire;
    private Categorie categorie;
    private double pret;
    private int calorii;
    private boolean esteVegetarian;

    /**
     * creeaza un produs
     *
     * @param id                id-ul produsului (pozitiv)
     * @param denumire          denumirea (intre 3 si 32 caractere)
     * @param categorie         categoria produsului
     * @param pret              pretul per portie (pozitiv)
     * @param calorii           numarul de calorii per portie (nenegativ)
     * @param esteVegetarian    true daca produsul este vegetarian
     * @throws CampGolException daca denumirea/categoria sunt goale/nule
     * @throws DateInvalideException    daca id-ul/denumirea/pretul/caloriile sunt invalide
     */
    public Produs(int id, String denumire, Categorie categorie, double pret, int calorii, boolean esteVegetarian) throws CampGolException, DateInvalideException {
        if (id <= 0) {
            throw new DateInvalideException("ID-ul produsului trebuie sa aiba o valoare pozitiva!");
        }
        this.id = id;
        setDenumire(denumire);
        setCategorie(categorie);
        setPret(pret);
        setCalorii(calorii);
        setEsteVegetarian(esteVegetarian);
    }

    public int getId() {
        return id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) throws CampGolException, DateInvalideException {
        if (denumire == null || denumire.trim().isEmpty()) {
            throw new CampGolException("Denumirea produsului nu poate fi goala!");
        }
        if (denumire.length() < 3 || denumire.length() > 32) {
            throw new DateInvalideException("Denumirea produsului trebuie sa aiba intre 3 si 32 de caractere!");
        }
        this.denumire = denumire;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) throws CampGolException {
        if (categorie == null) {
            throw new CampGolException("Categoria produsului nu poate fi nula!");
        }
        this.categorie = categorie;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) throws DateInvalideException {
        if (pret <= 0) {
            throw new DateInvalideException("Pretul produsului trebuie sa fie mai mare decat zero!");
        }
        this.pret = pret;
    }

    public int getCalorii() {
        return calorii;
    }

    public void setCalorii(int calorii) throws DateInvalideException {
        if (calorii < 0) {
            throw new DateInvalideException("Numarul de calorii al produsului nu poate avea o valoare negativa!");
        }
        this.calorii = calorii;
    }

    public boolean isEsteVegetarian() {
        return esteVegetarian;
    }

    public void setEsteVegetarian(boolean esteVegetarian) {
        this.esteVegetarian = esteVegetarian;
    }

    @Override
    public String toString() {
        return String.format("%-4d | %-32s | %6.2f RON | %4d kcal | vegetarian: %b",
                id, denumire, pret, calorii, esteVegetarian);
    }
}
