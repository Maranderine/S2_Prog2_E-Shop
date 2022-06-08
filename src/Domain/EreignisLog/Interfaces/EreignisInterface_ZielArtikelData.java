package Domain.EreignisLog.Interfaces;

/**
 * Interface für Ereignisse die einen Artikel bearbeiten
 */
public interface EreignisInterface_ZielArtikelData {

  /**
   * getter für den ALten Artikel Namen
   * 
   * @return String alter Artikel Name als String
   */
  public String getArtikelAltName();

  /**
   * getter für den ALten Artikel Bestand
   * 
   * @return int alter Artikel Bestand als int
   */
  public int getArtikelAltBestand();

  /**
   * getter für den ALten Artikel Preis
   * 
   * @return double alter Artikel Preis als double
   */
  public double getArtikelAltPreis();
}
