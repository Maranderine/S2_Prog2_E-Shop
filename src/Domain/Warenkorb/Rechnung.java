package Domain.Warenkorb;

import java.util.Date;
import java.util.HashMap;

import Domain.Artikel.Artikel;

public class Rechnung {
  private double betrag = 0;

  // Klassenvariablen
  private String rechnungsId;
  private String kaufDatum;
  // private inhalt;

  protected Rechnung(HashMap<Artikel, Integer> inhalt) {
    inhalt.forEach((artikel, bestand) -> {
      this.betrag = this.betrag + artikel.getPreis() * bestand;
    });

    Date date = new Date();
    this.kaufDatum = date.toString();
    this.rechnungsId = "1";
    // RechnungsId nur testweise
  }

  // Getter für das Kaufdatum, welcher das Kaufdatum übergibt.
  protected String getKaufDatum() {
    return kaufDatum;
  }

  // Getter für die Rechnungs ID der Rechnung, der die Rechnungs ID übergibt.
  protected String getRechnungsId() {
    return rechnungsId;
  }

  // Wenn abgefragt wird der Wert auf 0 und addiert den Preis des Artikels
  // dazu(z.B. DataObjects.Artikel & Anzahl)
  protected void getGesamtPreis() {
    this.betrag = 0;
  }

  public String toString() {
    // return this.artikelNr + " | " + this.name + " | " + this.bestand + " | " +
    // this.preis;
    return this.rechnungsId + "\t" + this.kaufDatum + "\t" + this.betrag;
  }
}
