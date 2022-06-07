package Domain.Artikel;

public class Massengutartikel extends Artikel {
    int minAnzahl;

    public Massengutartikel(int artikelNr, String bezeichnung, int bestand, double einzelpreis, int minAnzahl) {
        super(artikelNr, bezeichnung, bestand, einzelpreis);
        this.minAnzahl = minAnzahl;
    }

    public int getMinAnzahl() {
        return minAnzahl;
    }

    public void setMinAnzahl(int minAnzahl) {
        this.minAnzahl = minAnzahl;
    }
}

