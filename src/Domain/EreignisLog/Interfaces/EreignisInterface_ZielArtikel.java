package Domain.EreignisLog.Interfaces;

import Domain.Artikel.Artikel;

/**
 * Interface für Ereignisse die einen Artikel als Dokumentations ziel haben
 */
public interface EreignisInterface_ZielArtikel {

  /**
   * gets das Ziel Artikel Objekt
   * 
   * @return Artikel Objekt
   */
  public Artikel getZielArtikel();

  /**
   * gets die Ziel Artikel Identifikations Nummer
   * 
   * @return Identifikations nummer
   */
  public int getZielArtikelNummer();

  /**
   * gets den Ziel Artikel Name
   * 
   * @return Artikel Name
   */
  public String getZielArtikelName();

  /**
   * gets die Ziel Artikel Bestand nummer
   * 
   * @return Bestand nummer
   */
  public int getZielArtikelBestand();

  /**
   * gets den Ziel Artikel Preis nummer
   * 
   * @return Preis nummer
   */
  public double getZielArtikelPreis();
}
