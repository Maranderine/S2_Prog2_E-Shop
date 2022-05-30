package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;

import Domain.Artikel.Artikel;

//import Domain.EreignisLog;

public interface PersistenceManager {

	public void openForReading(String datenquelle) throws FileNotFoundException;
	
	public void openForWriting(String datenquelle) throws FileNotFoundException, IOException;
	
	public boolean close();

	/**
	 * Methode zum Einlesen der Buchdaten aus einer externen Datenquelle.
	 * 
	 * @return Buch-Objekt, wenn Einlesen erfolgreich, false null
	 */
	public Artikel ladeArtikel() throws IOException;

	/**
	 * Methode zum Schreiben der Buchdaten in eine externe Datenquelle.
	 * 
	 * @param b Buch-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Artikel b) throws IOException;

	
}