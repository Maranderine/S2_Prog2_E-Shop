package Domain.EreignisLog.Interfaces;

import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import common.EshopInterface.BenutzerType;

/**
 * Interface für Ereignisse die einene Calling Benutzer haben
 */
public interface EreignisInterface_CallingBenutzer {

  /**
   * gets das calling (aufrufenden) Benutzer Objekt
   * 
   * @return Benutzer calling (aufrufenden) Benutzer Objekt
   */
  public Benutzer getCallingBenutzer();

  /**
   * gets die calling (aufrufenden) Benutzer Identifikations Nummer
   * 
   * @return int Identifikations Nummer des calling (aufrufenden) Benutzers
   */
  public int getCallingBenutzerNummer();

  /**
   * gets den calling (aufrufenden) Benutzer typ als enum eintrag
   * 
   * @return Benutzerverwaltung.BenutzerType enum Benutzer typ des calling
   *         (aufrufenden) Benutzers
   */
  public BenutzerType getCallingBenutzerType();

  /**
   * gets den calling (aufrufenden) Benutzer namen als String
   * 
   * @return String Benutzer name des calling (aufrufenden) Benutzers
   */
  public String getCallingBenutzerName();
}