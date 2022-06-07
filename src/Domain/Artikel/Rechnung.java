package Domain.Artikel;

import Domain.BenutzerObjekte.*;
import Domain.Artikel.Artikel;
import java.util.Date;
import java.util.HashMap;

public class Rechnung {

    private String rechnungsID;
    private Date kaufDatum;
    private HashMap<Artikel,Integer> inhalt;

    //Konstruktor für Rechnung
    public Rechnung(kunde, Date kaufDatum, HashMap<Artikel,Integer> inhalt) {
        this.rechnungsID = getKundenNr() + "-" + +1;
        this.kaufDatum = kaufDatum;
        this.inhalt = inhalt;
    }

    // Gibt das Kaufdatum zurück
    public Date getKaufDatum() {
        return kaufDatum;
    }

    // Gibt die RechnungsID zurück
    public String getRechnungsId() {
        return rechnungsID;
    }

    // Gibt den Inhalt aus der HashMap-Array zurück
    public HashMap<Artikel, Integer> getInhalt() {
        return this.inhalt;
    }

    // Rechnet den Gesamtpreis aus
    public Double getGesamtPreis() {
        double betrag = 0;

        // Hier muss der Gesamtbetrag ausgerechnet werden
        // return null dann entfernen
        return null;
    }
}
