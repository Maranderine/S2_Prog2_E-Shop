package Domain;

import java.io.IOException;
import java.util.HashMap;

import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.Artikel.Lager;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.EreignisLogVerwaltung;
import Domain.Warenkorb.Rechnung;
import Domain.Warenkorb.WarenkorbVerwaltung;
import UserInterface.CUI;
import UserInterface.UserInterface;

/**
 * main eshop class
 * alle befehle der CUI laufen hier durch
 * 
 * @param artikelDox
 */
public class Eshop {

  private String artikelDoc;

  private Benutzerverwaltung BenutzerVw;
  private ArtikelVerwaltung ArtikelVw;
  private WarenkorbVerwaltung WarenkorbVw;
  private EreignisLogVerwaltung EreignisVw;

  public Eshop() {

    this.artikelDoc = "Artikel.txt";

    // create Verwaltungen
    try {
      BenutzerVw = new Benutzerverwaltung();
      ArtikelVw = new ArtikelVerwaltung();
      ArtikelVw.liesDaten(artikelDoc);
      WarenkorbVw = new WarenkorbVerwaltung();
      EreignisVw = new EreignisLogVerwaltung(this);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // create default admin mitarbeiter
    BV_mitarbeiterHinzufügen("Admin", "Admin", "123456");

    // #region TEMP PLEASE DELETE FOR FINAL PRODUCT

    // test user
    BV_kundeHinzufügen("test", "test", "123456", "test", "test");

    // Artikel
    /*
     * eshop.AV_addArtikel("Banane", 150, 1.99);
     * eshop.AV_addArtikel("Melone", 999999, 5.00);
     * eshop.AV_addArtikel("Seltener Fisch", 1, 99999);
     * eshop.AV_addArtikel("Apfel", 5, 1.77);
     */

    // LevelMove(MenuLevel.WARENKORB);
    // eshop.WV_setArtikel(eshop.AV_addArtikel("TEST1", 1, 1.99), 1);
    // eshop.WV_setArtikel(eshop.AV_addArtikel("TEST2", 1, 1.55), 1);
    // eshop.WV_setArtikel(eshop.AV_addArtikel("TEST3", 1, 1.66), 1);
    // eshop.WV_setArtikel(eshop.AV_addArtikel("TEST4", 1, 1.77), 1);

    // #endregion TEMP PLEASE DELETE FOR FINAL PRODUCT
  }

  // #region BenutzerVerwaltung

  /* admin/inobj method */
  private void BV_kundeHinzufügen(String name, String username, String password, String email, String address) {
    BenutzerVw.registrieren(name, username, password, email, address);
    // EreignisVw.ereignisAdd(user, type);
  }

  /**
   * create new user
   * 
   * @param callingUI the calling user obj, use "this"
   * @param name
   * @param username
   * @param password
   * @param email
   * @param address
   */
  public void BV_kundeHinzufügen(UserInterface callingUI, String name, String username, String password, String email,
      String address) {
    if (true)// add ereignis logging
      BV_kundeHinzufügen(name, username, password, email, address);

  }

  public void BV_mitarbeiterHinzufügen(String name, String username, String password) {
    BenutzerVw.registrieren(name, username, password);
  }

  public void BV_NutzerEntfernen(String username) {

  }

  /**
   * login to user profile
   * 
   * @param callingUI calling user Interface, use "this"
   * @param username
   * @param password
   * @return
   */
  public Benutzerverwaltung.AktiverBeutzerType login(UserInterface callingUI, String username, String password) {
    return BenutzerVw.login(callingUI, username, password);
  }

  /**
   * logout the user
   * 
   * @param callingUI calling user Interface, use "this"
   */
  public void logout(UserInterface callingUI) {
    BenutzerVw.logout(callingUI);
  }

  // aktive nutzer managen
  /**
   * returnt das Benutzer Objekt assoziert mit dem userHash
   * 
   * @param userHash
   * @return
   */
  public Benutzer BV_getAktiverBenutzer(byte[] userHash) {
    return BenutzerVw.getAktiverBenutzer(userHash);
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
  public Object WV_getWarenkorb() {
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

  public Rechnung WV_kaufen() {
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
    // EreignisVw.ereignisAdd();
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
  // #region Ereignis Log /////////////////////////////////////////

  /**
   * displays Ereignis Log in short
   * 
   * @return ereignis log as a string
   */
  public String EV_logDisplay() {
    return EreignisVw.displayLog();
  }

  /**
   * logt eine ereignis im ereignis log
   * 
   * @param userHash
   * @param type
   * @return
   */
  public boolean EV_logNewMitarbeiter(byte[] userHash) {
    return EreignisVw.neuesEreignis(userHash, "new User");
  }

  // #endregion ////////////////////////////////////////////////

  public void saveData() {
    try {
      ArtikelVw.schreibeDaten(artikelDoc);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * creates used User Interface object. for example CUI or GUI
   * 
   * @return UserInterface UserInterface Object
   */
  public UserInterface createUserInterface() {

    return new CUI(this);
  }

}
