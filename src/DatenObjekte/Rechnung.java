package DatenObjekte;

import java.util.Date;

public class Rechnung {

  // Klassenvariablen
  private String rechnungsId;
  private Date kaufDatum;
  // private inhalt;

  // Konstruktor für Rechnung
  // public Rechnung(BenutzerObjekte.Kunde kunde, Date kaufDatum, inhalt) {
  // this.rechnungsId = "0-" + kunde.getKundenNr() + "-" + (100 +
  // kunde.getKundenRechnungen().size()); //info ????
  // this.kaufDatum = kaufDatum;
  // this.inhalt = inhalt;
  // }

  // Getter für das Kaufdatum, welcher das Kaufdatum übergibt.
  public Date getKaufDatum() {
    return kaufDatum;
  }

  // Getter für die Rechnungs ID der Rechnung, der die Rechnungs ID übergibt.
  public String getRechnungsId() {
    return rechnungsId;
  }

  // Wenn abgefragt wird der Wert auf 0 und addiert den Preis des Artikels
  // dazu(z.B. DataObjects.Artikel & Anzahl)
  public void getGesamtPreis() {
    double betrag = 0;
  }
}
