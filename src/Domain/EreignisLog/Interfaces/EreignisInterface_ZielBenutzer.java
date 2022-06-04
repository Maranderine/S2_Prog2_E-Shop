package Domain.EreignisLog.Interfaces;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;

/**
 * Interface für Ereignisse die einen Benutzer als Dokumentations ziel haben
 */
public interface EreignisInterface_ZielBenutzer {

  /**
   * getter für das Ziel Benutzer Objekt
   * 
   * @return Benutzer Ziel Benutzer Objekt
   */
  public Benutzer getZielBenutzer();

  /**
   * getter für das Ziel Benutzer Identifikations Nummer
   * 
   * @return int Ziel Benutzer Identifikations Nummer
   */
  public int getZielBenutzerNummer();

  /**
   * getter für den Ziel Benutzer typ, teil des Benutzerverwaltung.BeutzerType
   * enums
   * 
   * @return Benutzerverwaltung.BeutzerType Ziel Benutzer typ als enum eintrag
   */
  public Benutzerverwaltung.BeutzerType getZielBenutzerType();

  /**
   * getter für den Ziel Benutzer Name
   * 
   * @return String Ziel Benutzer Name als String
   */
  public String getZielBenutzerName();
}