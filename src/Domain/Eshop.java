package Domain;

import java.io.IOException;
import java.util.HashMap;

import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.Artikel.Lager;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.EreignisLogVerwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.Warenkorb.Rechnung;
import Domain.Warenkorb.WarenkorbVerwaltung;
import UserInterface.CUI;
import UserInterface.UserInterface;

/**
 * main eshop class
 * alle befehle der CUI laufen hier durch
 */
public class Eshop {

  private String artikelDoc = "";
  private String mitarbeiterDoc = "";
  private String kundenDoc = "";

  private Benutzerverwaltung BenutzerVw;
  private ArtikelVerwaltung ArtikelVw;
  private WarenkorbVerwaltung WarenkorbVw;
  private EreignisLogVerwaltung EreignisVw;


  public Eshop(String artikelDox, String kundenDox, String mitarbeiterDox){

    this.artikelDoc = artikelDox;
    this.mitarbeiterDoc = mitarbeiterDox;
    this.kundenDoc = kundenDox;

  try {
      BenutzerVw = new Benutzerverwaltung();
      if (!(kundenDoc.equals(""))) {
        BenutzerVw.load(kundenDoc);
      }
      ArtikelVw = new ArtikelVerwaltung();
      if (!(artikelDoc.equals(""))) {
        ArtikelVw.load(artikelDoc);
      }
      WarenkorbVw = new WarenkorbVerwaltung();
      EreignisVw = new EreignisLogVerwaltung(this, BenutzerVw, ArtikelVw);
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

  /**
   * create new user
   * 
   * @param name
   * @param username
   * @param password
   * @param email
   * @param address
   */
  public void BV_kundeHinzufügen(String name, String username, String password, String email, String address) {
    BenutzerVw.registrieren(name, username, password, email, address);
    // EreignisVw.ereignisAdd(user, type);
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
  public Benutzerverwaltung.BeutzerType login(UserInterface callingUI, String username, String password) {
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
    Rechnung rechnung = new Rechnung(WarenkorbVw.ArtikelKaufen());
    WarenkorbVw.clearAll();
    return rechnung;

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
  public Artikel AV_addArtikel(byte[] userHash, String name, int bestand, double einzelpreis) {

    Artikel artikel = ArtikelVw.addArtikel(name, bestand, einzelpreis);
    // ereignis loggen
    // TODO: EVENT - event basiert

    EV_EreignisArtikelCreate(userHash, "Artikel Erstellen", artikel);

    return artikel;
  }

  public boolean AV_deleteArtikel(byte[] userHash, String name) {

    Artikel artikel = ArtikelVw.findArtikelByName(name);
    if (artikel != null) {
      // TODO: EVENT - basiert und fehlschlag
      EV_EreignisArtikelDelete(userHash, "Artikel Löschen", artikel);

      return ArtikelVw.deleteArtikel(artikel);
    }
    return false;
  }

  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param name      artikel name
   * @param neuerName artikel neuer name
   * @return boolean obs geklappt hat
   */
  public boolean AV_setArtikel(byte[] userHash, String name, String neuerName) {

    Artikel artikel = ArtikelVw.findArtikelByName(name);
    if (artikel != null) {
      String nameAlt = ArtikelVw.getArtikelName(artikel);
      // set neuer Name
      boolean bool = ArtikelVw.setArtikelName(name, neuerName);
      // logt neuen und alten Namen
      EV_EreignisArtikelData(userHash, "Artikel Name änderung", artikel, nameAlt, null, null);

      return bool;
    }
    return false;
  }

  /**
   * set artikel data bestand
   * 
   * @param userHash userHash
   * @param name     artikel name
   * @param bestand  artikel neuer bestand
   * @return boolean obs geklappt hat
   */
  public boolean AV_setArtikel(byte[] userHash, String name, int bestand) {

    Artikel artikel = ArtikelVw.findArtikelByName(name);
    if (artikel != null) {
      int bestandAlt = ArtikelVw.getArtikelBestand(artikel);
      // set neuer bestand
      boolean bool = ArtikelVw.setArtikelBestand(name, bestand);
      // logt neuen und alten bestand
      EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, null, bestandAlt, null);

      return bool;
    }
    return false;
  }

  /**
   * set artikel data preis
   * 
   * @param userHash userHash
   * @param name     artikel name
   * @param preis    artikel neuer preis
   * @return boolean obs geklappt hat
   */
  public boolean AV_setArtikel(byte[] userHash, String name, double preis) {

    Artikel artikel = ArtikelVw.findArtikelByName(name);
    if (artikel != null) {
      double preisAlt = ArtikelVw.getArtikelPreis(artikel);
      // set neuer preis
      boolean bool = ArtikelVw.setArtikelPreis(name, preis);
      // logt neuen und alten preis
      EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, null, null, preisAlt);

      return bool;
    }
    return false;
  }

  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param name      artikel name
   * @param neuerName artikel neuer name
   * @param bestand   artikel neuer bestand
   * @param preis     artikel neuer preis
   * @return boolean obs geklappt hat
   */
  public boolean AV_setArtikel(byte[] userHash, String name, String neuerName, int bestand, double preis) {

    Artikel artikel = ArtikelVw.findArtikelByName(name);
    if (artikel != null) {
      String nameAlt = ArtikelVw.getArtikelName(artikel);
      int bestandAlt = ArtikelVw.getArtikelBestand(artikel);
      double preisAlt = ArtikelVw.getArtikelPreis(artikel);
      // set neuer Name
      boolean bool1 = ArtikelVw.setArtikelName(name, neuerName);
      boolean bool2 = ArtikelVw.setArtikelBestand(name, bestand);
      boolean bool3 = ArtikelVw.setArtikelPreis(name, preis);
      // logt neuen und alten preis
      EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, nameAlt, bestandAlt, preisAlt);

      return (bool1 && bool2 && bool3);
    }
    return false;
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
    return EreignisVw.displayLog(false);
  }

  // neue ereignisse

  /**
   * loggt neues Artikel Ereignis welches von dem Erstellen eines Artikels
   * handelt,
   * returnt true wenn ereignis erstellt wurde
   * 
   * @param userHash
   * @param ereignisDesc
   * @param artikel
   * @return
   */
  private boolean EV_EreignisArtikelCreate(byte[] userHash, String ereignisDesc, Artikel artikel) {
    Ereignis ereignis = EreignisVw.Ereignis_EreignisArtikelCreate(userHash, ereignisDesc, artikel);

    return true;
  }

  /**
   * loggt neues Artikel Ereignis welches von dem Löschen eines Artikels handelt,
   * returnt true wenn ereignis erstellt wurde
   * 
   * @param userHash
   * @param ereignisDesc
   * @param artikel
   * @return
   */
  private boolean EV_EreignisArtikelDelete(byte[] userHash, String ereignisDesc, Artikel artikel) {
    Ereignis ereignis = EreignisVw.Ereignis_EreignisArtikelDelete(userHash, ereignisDesc, artikel);

    return true;
  }

  /**
   * loggt neues Artikel Ereignis welches sich mit daten änderungen befasst,
   * returnt true wenn ereignis erstellt wurde
   * 
   * @param userHash
   * @param ereignisDesc
   * @param artikel      artiel onjekt
   * @param AaltName     alte daten oder null (logt dann aktuelle daten)
   * @param AaltBestand  alte daten oder null (logt dann aktuelle daten)
   * @param AaltPreis    alte daten oder null (logt dann aktuelle daten)
   * @return
   */
  private boolean EV_EreignisArtikelData(byte[] userHash, String ereignisDesc, Artikel artikel, String AaltName,
      Integer AaltBestand, Double AaltPreis) {

    if (AaltName == null)
      AaltName = ArtikelVw.getArtikelName(artikel);
    if (AaltBestand == null)
      AaltBestand = ArtikelVw.getArtikelBestand(artikel);
    if (AaltPreis == null)
      AaltPreis = ArtikelVw.getArtikelPreis(artikel);

    Ereignis ereignis = EreignisVw.Ereignis_EreignisArtikelData(userHash, ereignisDesc, artikel, AaltName, AaltBestand,
        AaltPreis);

    return true;
  }

  // /**
  // * loggt neues System Ereignis welches einen Artikel betrifft, returnt true
  // wenn
  // * ereignis erstellt wurde
  // *
  // * @param ereignisDesc
  // * @param artikel
  // * @return
  // */
  // private boolean EV_EreignisSystemArtikel(String ereignisDesc, Artikel
  // artikel) {
  // Ereignis ereignis = EreignisVw.Ereignis_EreignisSystemArtikel(ereignisDesc,
  // artikel);

  // return true;
  // }

  // #endregion ////////////////////////////////////////////////

  public void saveData() {
    try {
      ArtikelVw.save(artikelDoc);
    BenutzerVw.save(kundenDoc);
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
