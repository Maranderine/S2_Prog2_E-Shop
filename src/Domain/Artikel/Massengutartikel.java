package Domain.Artikel;

public class Massengutartikel extends Artikel {
    int minAnzahl;

    public Massengutartikel(int artikelNr, String bezeichnung, int bestand, double einzelpreis, int minAnzahl) {
        super(artikelNr, bezeichnung, bestand, einzelpreis);
        this.minAnzahl = minAnzahl;
    }

    // Gibt die MinAnzahl aus
    public int getMinAnzahl() {
        return minAnzahl;
    }

    // Setzt die MinAnzahl als Integer-Wert fest
    public void setMinAnzahl(int minAnzahl) {
        this.minAnzahl = minAnzahl;
    }
}