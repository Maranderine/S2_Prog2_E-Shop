package Domain;

import java.util.HashMap;

import BenutzerObjekte.Benutzer;
import BenutzerObjekte.Benutzerverwaltung;
import DatenObjekte.Artikel;
import Domain.Warenkorb.WarenkorbVerwaltung;

public class Eshop {

  static private Benutzerverwaltung BenutzerVw;
  static private ArtikelVerwaltung ArtikelVw;
  static private WarenkorbVerwaltung WarenkorbVw;

  public Eshop() {
    BenutzerVw = new Benutzerverwaltung();
    ArtikelVw = new ArtikelVerwaltung();
    WarenkorbVw = new WarenkorbVerwaltung();
  }

  // #region NutzerVerwaltung

  public void KundeHinzufügen(String name, String username, String password, int nr, String email, String adress) {
    BenutzerVw.registrieren(name, username, password, nr, email, adress);
  }

  public void MitarbeiterHinzufügen(String username) {

  }

  public void NutzerEntfernen(String username) {

  }

  public void Login() {
    // return BenutzerVw.Login();
  }

  public void Logout() {
    BenutzerVw.Logout();
  }

  // #endregion
  // #region warenkorb
  /**
   * Gibt Wahrenkorb Inhalt zurüc
   * 
   * @return HashMap<Artikel, Integer>
   */
  public HashMap<Artikel, Integer> WK_getInhalt() {
    return WarenkorbVw.getInhalt();
  }

  /**
   * Setzt den gesamten inhalt des warenkorbs
   * 
   * @param inhalt
   */
  public void WK_setInhalt(HashMap<Artikel, Integer> inhalt) {
    WarenkorbVw.setInhalt(inhalt);
  }

  /**
   * erstellt einen neuen eintrag oder ändert einen vorhandenen
   * 
   * @param artikel artikel objekt
   * @param integar artikel stückzahl
   */
  public void WK_setArtikel(Artikel artikel, Integer integar) {
    WarenkorbVw.setArtikel(artikel, integar);
  }

  /**
   * entfernt einen artikel aus der map
   * 
   * @param artikel artikel zu entfernen
   */
  public void WK_removeArtikel(Artikel artikel) {
    WarenkorbVw.removeArtikel(artikel);
  }

  /**
   * löscht den gesamten inhalt des Warenkorbes
   */
  public void WK_clearAll() {
    WarenkorbVw.clearAll();
  }
  // #endregion

}
