package DatenObjekte;

public class Artikel {

    private int artikelNr;
    private String name;
    private int bestand;
    private double preis;

    public Artikel(int artikelNr, String bezeichnung,int bestand, double einzelpreis) {
        this.artikelNr = artikelNr;
        this.name = name;
        this.bestand = bestand;
        this.preis = preis;
    }

    public int getArtikelNr() {
        return this.artikelNr;
    }

    public String getName() {
        return this.name;
    }

    public int getBestand() {
        return this.bestand;
    }

    public double getPreis() {
        return this.preis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    public void setPreis(double Preis) {
        this.preis = preis;
    }

    public String toString() {
        return this.name;
    }
}