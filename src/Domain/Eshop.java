package Domain;

import java.io.IOException;
import java.util.HashMap;

import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.Artikel.Lager;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.EreignisLogVerwaltung;
import Domain.Search.SuchOrdnung;
import Domain.Warenkorb.Rechnung;
import Domain.Warenkorb.WarenkorbVerwaltung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelNichtGenugBestand;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import UserInterface.CUI;
import UserInterface.UserInterface;

/**
 * main eshop class
 * alle befehle der CUI laufen hier durch
 */
public class Eshop {

  private Benutzerverwaltung BenutzerVw;
  private ArtikelVerwaltung ArtikelVw;
  private WarenkorbVerwaltung WarenkorbVw;
  private EreignisLogVerwaltung EreignisVw;

  public Eshop(String artikelDox, String benutzerDox, String ereignisDox) {

    BenutzerVw = new Benutzerverwaltung(benutzerDox);
    ArtikelVw = new ArtikelVerwaltung(this, artikelDox);
    WarenkorbVw = new WarenkorbVerwaltung(this, ArtikelVw);
    EreignisVw = new EreignisLogVerwaltung(this, ereignisDox, BenutzerVw, ArtikelVw);

    // give Ereignis verwaltung
    BenutzerVw.ereignisLogVerwaltung = EreignisVw;
    ArtikelVw.ereignisLogVerwaltung = EreignisVw;
    WarenkorbVw.ereignisLogVerwaltung = EreignisVw;

    // #region TEMP PLEASE DELETE FOR FINAL PRODUCT

    // BV_mitarbeiterHinzufügen("Admin", "Admin", "12345");

    // Admin 12345
    // test 12345

    // Artikel

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

  /**
   * Kauft alle artikel im Warenkorb.
   * Aktualisiert bestand für alle
   * und erstellt entspechende events
   * 
   * @param userHash Benutzer Identifikator der die funtion ausführt
   * @return rechnung
   * @throws ExceptionArtikelCollection
   */
  public Rechnung WV_kaufen(byte[] userHash) throws ExceptionArtikelCollection {
    // TODO kauf event was alle artikel anzeigt und deren neue bestände, anstadt

    Rechnung rechnung = WarenkorbVw.ArtikelKaufen();

    // setz neuen Bestand
    rechnung.getInhalt().forEach((artikel, anzahl) -> {

      try {
        AV_setArtikel(userHash, artikel, ArtikelVw.getArtikelBestand(artikel) - anzahl);
      } catch (ExceptionArtikelUngültigerBestand e) {
        // TODO falscher bestand gesetzt, was machen?
        e.printStackTrace();
      }

    });

    // duzende Artikel.änderungs events
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
   * 
   * @param searchTerm
   * @return SuchOrdnung
   */
  public SuchOrdnung AV_sucheArtikel(String searchTerm) {
    return ArtikelVw.suchArtikel(searchTerm);
  }

  /**
   * Add Artikel to artikelListe
   * 
   * @param name
   * @param bestand
   * @param einzelpreis
   * @throws ExceptionArtikelExistiertBereits
   */
  public Artikel AV_addArtikel(byte[] userHash, String name, int bestand, double einzelpreis)
      throws ExceptionArtikelExistiertBereits {

    Artikel artikel = ArtikelVw.addArtikel(name, bestand, einzelpreis);
    // ereignis loggen
    // TODO: EVENT - event basiert

    EV_EreignisArtikelCreate(userHash, "Artikel Erstellen", artikel);

    return artikel;
  }

  public boolean AV_deleteArtikel(byte[] userHash, String name) throws ExceptionArtikelNichtGefunden {

    Artikel artikel = ArtikelVw.findArtikelByName(name);

    // TODO: EVENT - basiert und fehlschlag
    EV_EreignisArtikelDelete(userHash, "Artikel Löschen", artikel);
    return ArtikelVw.deleteArtikel(artikel);
  }

  // #region set artikel
  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param artikel   artikel obj
   * @param neuerName artikel neuer name
   * @throws ExceptionArtikelNameExistiertBereits
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName)
      throws ExceptionArtikelNameExistiertBereits {
    String nameAlt = ArtikelVw.getArtikelName(artikel);
    // set neuer Name
    ArtikelVw.setArtikelName(artikel, neuerName);
    // logt neuen und alten Namen
    EV_EreignisArtikelData(userHash, "Artikel Name änderung", artikel, nameAlt, null, null);
  }

  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param name      artikel name
   * @param neuerName artikel neuer name
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelNameExistiertBereits
   */
  public void AV_setArtikel(byte[] userHash, String name, String neuerName)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelNameExistiertBereits {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), neuerName);
  }

  /**
   * set artikel data bestand
   * 
   * @param userHash userHash
   * @param artikel  artikel obj
   * @param bestand  artikel neuer bestand
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, int bestand) throws ExceptionArtikelUngültigerBestand {

    if (artikel != null) {
      int bestandAlt = ArtikelVw.getArtikelBestand(artikel);
      // set neuer bestand
      ArtikelVw.setArtikelBestand(artikel, bestand);
      // logt neuen und alten bestand
      EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, null, bestandAlt, null);
    }
  }

  /**
   * set artikel data bestand
   * 
   * @param userHash userHash
   * @param name     artikel name
   * @param bestand  artikel neuer bestand
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void AV_setArtikel(byte[] userHash, String name, int bestand)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), bestand);
  }

  /**
   * set artikel data preis
   * 
   * @param userHash userHash
   * @param artikel  artikel obj
   * @param preis    artikel neuer preis
   * @throws ExceptionArtikelNichtGefunden
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, double preis)
      throws ExceptionArtikelNichtGefunden {

    double preisAlt = ArtikelVw.getArtikelPreis(artikel);
    // set neuer preis
    ArtikelVw.setArtikelPreis(artikel, preis);
    // logt neuen und alten preis
    EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, null, null, preisAlt);
  }

  /**
   * set artikel data preis
   * 
   * @param userHash userHash
   * @param name     artikel name
   * @param preis    artikel neuer preis
   * @throws ExceptionArtikelNichtGefunden
   */
  public void AV_setArtikel(byte[] userHash, String name, double preis)
      throws ExceptionArtikelNichtGefunden {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), preis);
  }

  /**
   * set artikel data
   * 
   * @param userHash  userHash
   * @param artikel   artikel obj
   * @param neuerName artikel neuer name
   * @param bestand   artikel neuer bestand
   * @param preis     artikel neuer preis
   * @throws ExceptionArtikelUngültigerBestand
   * @throws ExceptionArtikelNichtGefunden
   */
  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {

    if (artikel != null) {
      String nameAlt = ArtikelVw.getArtikelName(artikel);
      int bestandAlt = ArtikelVw.getArtikelBestand(artikel);
      double preisAlt = ArtikelVw.getArtikelPreis(artikel);
      // set neuer Name
      try {
        ArtikelVw.setArtikelName(artikel, neuerName);
        ArtikelVw.setArtikelBestand(artikel, bestand);
        ArtikelVw.setArtikelPreis(artikel, preis);
      } catch (Exception e) {

      }

      // logt neuen und alten preis
      EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, nameAlt, bestandAlt, preisAlt);
    }
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
   * @throws ExceptionArtikelNichtGefunden
   * @throws ExceptionArtikelUngültigerBestand
   */
  public void AV_setArtikel(byte[] userHash, String name, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), neuerName, bestand, preis);
  }

  // #endregion

  /**
   * find Artikel by name in artikelListe
   * 
   * @param name of artikel
   * @return Artikel type object or null
   * @throws ExceptionArtikelNichtGefunden
   */
  public Artikel AV_findArtikelByName(String name) throws ExceptionArtikelNichtGefunden {
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
    EreignisVw.Ereignis_EreignisArtikelCreate(userHash, ereignisDesc, artikel);

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
    EreignisVw.Ereignis_EreignisArtikelDelete(userHash, ereignisDesc, artikel);

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

    EreignisVw.Ereignis_EreignisArtikelData(userHash, ereignisDesc, artikel, AaltName, AaltBestand,
        AaltPreis);

    return true;
  }

  // #endregion ////////////////////////////////////////////////

  public void saveData() {
    try {
      BenutzerVw.save();
      ArtikelVw.save();
      EreignisVw.save();
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
