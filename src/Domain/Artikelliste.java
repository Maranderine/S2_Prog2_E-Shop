package Domain;

import Exceptions.ArtikelExistiertBereitsException;
import DatenObjekte.Artikel;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class Artikelliste {
    private String datei = "";

    private Artikelliste AL;
    // private KundenVerwaltung kundenVW;
    // hier weitere Verwaltungsklassen, z.B. für Autoren oder Angestellte

    public Artikelliste(String datei) throws IOException {
        this.datei = datei;

        // Buchbestand aus Datei einlesen
        AL = new Artikelliste();
        AL.liesDaten(datei+"_B.txt");

//		// Kundenkartei aus Datei einlesen
//		meineKunden = new KundenVerwaltung();
//		meineKunden.liesDaten(datei+"_K.txt");
//		meineKunden.schreibeDaten(datei+"_K.txt");
    }

    public List<Artikel> gibAlleBuecher() {
        // einfach delegieren an meineBuecher
        return AL.getBestand();
    }

    public List<Artikel> sucheNachTitel(String titel) {
        // einfach delegieren an meineBuecher
        return AL.suche(titel);
    }

    public Artikel add(String titel, int nummer) throws ArtikelExistiertBereitsException {
        Artikel a = new Artikel(titel, nummer);
        AL.einfuegen(a);
        return a;
    }

    /**
     * Methode zum Löschen eines Buchs aus dem Bestand.
     * Es wird nur das erste Vorkommen des Buchs gelöscht.
     *
     * @param titel Titel des Buchs
     * @param nummer Nummer des Buchs
     */
    public void delete(String titel, int nummer) {
        Artikel a = new Artikel(titel, nummer);
        AL.loeschen(a);
    }

    /**
     * Methode zum Speichern des Buchbestands in einer Datei.
     *
     * @throws IOException z.B. wenn Datei nicht existiert
     */
    public void schreibeBuecher() throws IOException {
        AL.schreibeDaten(datei+"_Artikelliste.txt");
    }
}
