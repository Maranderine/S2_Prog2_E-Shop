package persistence;

import java.io.IOException;
import java.util.Vector;

import Domain.Artikel.Artikel;

//import Domain.EreignisLog;

public interface PersistenceManager {

  public Vector<Artikel> loadArticle(String datenquelle) throws IOException;

  public boolean saveArticle(String datenquelle, Vector<Artikel> objektListe) throws IOException;

  public Vector<? extends Object> loadObjekt(String datei) throws IOException;

  public boolean saveObjekt(String datenquelle, Vector<? extends Object> objektListe) throws IOException;
}