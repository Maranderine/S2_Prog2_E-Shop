package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Domain.Artikel.Artikel;

public class FilePersistenceManager implements PersistenceManager {

	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader("Artikel.txt"));
	}

	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}

	public boolean close() {
		if (writer != null)
			writer.close();
		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
		}

		return true;
	}


	public Artikel ladeArtikel() throws IOException {
		// Titel einlesen
		String stringNr = liesZeile();
        if(stringNr == null){return null;}
        int artikelNr = Integer.parseInt(stringNr);
		String name = liesZeile();
		int bestand = Integer.parseInt(liesZeile());
        double preis = Double.parseDouble(liesZeile());
		
		// neues Buch-Objekt anlegen und zurückgeben
		return new  Artikel(artikelNr, name, bestand, preis);
	}

	/**
	 * Methode zum Schreiben der Buchdaten in eine externe Datenquelle.
	 * Das Verfügbarkeitsattribut wird in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @param b Buch-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Artikel a) throws IOException {
		// Titel, Nummer und Verfügbarkeit schreiben
		schreibeZeile(a.getArtikelNr()+"");
//		schreibeZeile(Integer.valueOf(b.getNummer()).toString());
		schreibeZeile(a.getName());
		schreibeZeile(a.getBestand()+"");
        schreibeZeile(a.getPreis()+"");

		return true;
	}

	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
}


