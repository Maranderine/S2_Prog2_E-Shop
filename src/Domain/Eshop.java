package Domain;

import java.util.HashMap;

import BenutzerObjekte.Benutzerverwaltung;
import DatenObjekte.Artikel;
import DatenObjekte.Rechnung;
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
  public void kundeHinzufügen(String name, String username, String password, String email, String address) {
    BenutzerVw.registrieren(name, username, password, email, address);
  }

  public void mitarbeiterHinzufügen(String name, String username, String password) {
    BenutzerVw.registrieren(name, username, password);
  }

  public void NutzerEntfernen(String username) {

  }

  public Benutzerverwaltung.AktiverNutzer login(String username, String password) {
    return BenutzerVw.login(username, password);
  }

  public void logout() {
    BenutzerVw.logout();
  }

  // #endregion
  // #region Warenkorb
  /**
   * gibt Warenkorb Inhalt zurück
   * 
   * @return HashMap<Artikel, Integer>
   */
  public HashMap<Artikel, Integer> WK_getInhalt() {
    return WarenkorbVw.getInhalt();
  }

  /**
   * gibt warenkorb
   * 
   * @return
   */
  public Object WK_getWarenkorb() {
    return WarenkorbVw.getWarenkorb();
  }

  /**
   * erstellt einen neuen Eintrag oder ändert einen vorhandenen
   * 
   * @param artikel artikel object
   * @param integer artikel Stückzahl
   */
  public void WV_setArtikel(Artikel artikel, int integer) {
    WarenkorbVw.setArtikel(artikel, integer);
  }

  /**
   * entfernt einen artikel aus der map
   * 
   * @param artikel artikel zu entfernen
   */
  public void WV_removeArtikel(Artikel artikel) {
    WarenkorbVw.removeArtikel(artikel);
  }

  /**
   * löscht den gesamten inhalt des Warenkorbes
   */
  public void WV_clearAll() {
    WarenkorbVw.clearAll();
  }

  public Rechnung WV_kaufen(){
    return WarenkorbVw.ArtikelKaufen();
  }
  // #endregion Warenkorb
  // #region Artikelvw
  /**
   * @author Maranderine
   */
  public Lager AV_alleArtikel() {
    return ArtikelVw.alleArtikel();
  }

  /**
   * Add Artikel to artikelListe
   * 
   * @param name
   * @param bestand
   * @param einzelpreis
   */
  public Artikel AV_addArtikel(String name, int bestand, double einzelpreis) {
    return ArtikelVw.addArtikel(name, bestand, einzelpreis);
  }

  public boolean AV_deleteArtikel(String name) {
    return ArtikelVw.deleteArtikel(name);
  }

  public boolean AV_setArtikelBestand(String name, int bestand) {
    return ArtikelVw.setArtikelBestand(name, bestand);
  }
  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object or null
   */
  public Artikel AV_findArtikelByName(String name) {
    return ArtikelVw.findArtikelByName(name);
  }

  // #endregion Artikel
}
