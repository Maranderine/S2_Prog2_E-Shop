package Domain.Warenkorb;

import java.util.Date;
import java.util.HashMap;

import Domain.Artikel.Artikel;

public class Rechnung {
  private double betrag = 0;
  private String kaufArtikel = "";
  private String rechnungsId;
  private Date kaufDatum;

  private HashMap<Artikel, Integer> inhalt;
  // private inhalt;

  public Rechnung(HashMap<Artikel, Integer> WK_inhalt) {
    WK_inhalt.forEach((artikel, bestand) -> {
      betrag += artikel.getPreis() * bestand;
      kaufArtikel += artikel.toString() + "\t" + bestand + "\n";
    });

    kaufDatum = new Date();
    // TODO rechnungs id
    rechnungsId = "1";

    // RechnungsId nur testweise
  }

  // Getter für das Kaufdatum, welcher das Kaufdatum übergibt.
  protected String getKaufDatum() {
    return kaufDatum.toString();
  }

  // Getter für die Rechnungs ID der Rechnung, der die Rechnungs ID übergibt.
  protected String getRechnungsId() {
    return rechnungsId;
  }

  // Wenn abgefragt wird der Wert auf 0 und addiert den Preis des Artikels
  // dazu(z.B. DataObjects.Artikel & Anzahl)
  protected double getGesamtPreis() {
    return this.betrag = 0;
  }

  // Gibt den Inhalt aus der HashMap-Array zurück
  public HashMap<Artikel, Integer> getInhalt() {
    return this.inhalt;
  }

  public String toString() {
    // return this.artikelNr + " | " + this.name + " | " + this.bestand + " | " +
    // this.preis;
    return "Rechnungs Id: " + this.rechnungsId + "\n" +
        "Datum: " + this.getKaufDatum() + "\n" +
        "________________________" + "\n" +
        kaufArtikel + "________________________" +
        "\n" + "Gesamtbetrag: " +
        this.betrag + "\n";
  }
}