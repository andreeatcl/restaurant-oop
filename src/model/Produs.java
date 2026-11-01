package model;

public class Produs {
    private final int id;
    private String denumire;
    private Categorie categorie;
    private double pret;
    private int calorii;

    public Produs(int id, String denumire, Categorie categorie, double pret, int calorii) {
        this.id = id;
        this.denumire = denumire;
        this.categorie = categorie;
        this.pret = pret;
        this.calorii = calorii;
    }

    public int getId() {
        return id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCalorii() {
        return calorii;
    }

    public void setCalorii(int calorii) {
        this.calorii = calorii;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", categorie=" + categorie +
                ", pret=" + pret +
                ", calorii=" + calorii +
                '}';
    }
}
