package persistence;

import java.io.IOException;
import java.util.Vector;

import Domain.Artikel.Artikel;

//import Domain.EreignisLog;

public interface PersistenceManager {

  public Vector<String[]> loadData(String artikelDoc) throws IOException;

  public boolean saveData(String artikelDoc, Vector<Artikel> objektListe) throws IOException;

  public Vector<? extends Object> loadObjekt(String datei) throws IOException;

  public boolean saveObjekt(String datenquelle, Vector<? extends Object> objektListe) throws IOException;
}