package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import Domain.Artikel.Artikel;
import Domain.BenutzerObjekte.Benutzer;

//import Domain.EreignisLog;

public interface PersistenceManager {

	public Vector<Artikel> loadArticle(String datenquelle) throws IOException;

	public boolean saveArticle(String datenquelle,  Vector<Artikel> artikelListe) throws IOException;
	public Vector<Benutzer> loadNutzer(String datei);
	public boolean saveNutzer(String datenquelle, Vector<Benutzer> nutzerListe);
	
	public boolean close();

}