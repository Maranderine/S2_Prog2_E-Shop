package Domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import Domain.Artikel.Artikel;
import Domain.Artikel.ArtikelVerwaltung;
import Domain.BenutzerObjekte.Benutzer;
import Domain.BenutzerObjekte.Benutzerverwaltung;
import Domain.EreignisLog.EreignisLogVerwaltung;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.EreignisLog.Ereignisse.Artikel.EreignisArtikel;
import Domain.Networking.SocketVerwaltung;
import Domain.Search.SuchOrdnung;
import Domain.Warenkorb.Rechnung;
import Domain.Warenkorb.Warenkorb;
import Domain.Warenkorb.WarenkorbVerwaltung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtErstelltWerden;
import Exceptions.Artikel.ExceptionArtikelKonnteNichtGelöschtWerden;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameUngültig;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import Exceptions.Benutzer.ExceptionBenutzerNichtGefunden;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import UserInterface.CUI;
import UserInterface.UserInterface;
import common.EshopInterface;

/**
 * main eshop class
 * alle befehle der CUI laufen hier durch
 */
public class Eshop implements EshopInterface {

  private Benutzerverwaltung BenutzerVw;
  private ArtikelVerwaltung ArtikelVw;
  private WarenkorbVerwaltung WarenkorbVw;
  private EreignisLogVerwaltung EreignisVw;

  public Eshop(String host, int port) {

    BenutzerVw = new Benutzerverwaltung();
    ArtikelVw = new ArtikelVerwaltung(this);
    WarenkorbVw = new WarenkorbVerwaltung(this, ArtikelVw);
    EreignisVw = new EreignisLogVerwaltung(this, BenutzerVw, ArtikelVw);

    // give Ereignis verwaltung
    BenutzerVw.ereignisLogVerwaltung = EreignisVw;
    ArtikelVw.ereignisLogVerwaltung = EreignisVw;m
    WarenkorbVw.ereignisLogVerwaltung = EreignisVw;

    // #region TEMP PLEASE DELETE FOR FINAL PRODUCT

    // BV_mitarbeiterHinzufügen("Admin", "Admin", "12345");

    // existierende Mitarbeiter
    // Admin 12345
    // a a

    // existierende kunden
    // test 12345
    // t t

    // Artikel

    // #endregion TEMP PLEASE DELETE FOR FINAL PRODUCT

  }

  // #region BenutzerVerwaltung

  public void BV_kundeHinzufügen(String name, String username, String password, String email, String address)
      throws ExceptionBenutzerNameUngültig {
    BenutzerVw.registrieren(name, username, password, email, address);
    // EreignisVw.ereignisAdd(user, type);
  }

  public void BV_mitarbeiterHinzufügen(String name, String username, String password)
      throws ExceptionBenutzerNameUngültig {
    BenutzerVw.registrieren(name, username, password);
  }

  public void BV_NutzerEntfernen(String username) throws ExceptionBenutzerNichtGefunden {
    BenutzerVw.deleteBenutzer(username);
  }

  public Benutzer BV_getUserActive(byte[] userHash) {
    return BenutzerVw.getAktiverBenutzer(userHash);
  }

  public Vector<Benutzer> BV_getAllNutzer() {
    return BenutzerVw.getBenutzerList();
  }

  public Benutzerverwaltung.BeutzerType login(UserInterface callingUI, String username, String password) {
    return BenutzerVw.login(callingUI, username, password);
  }

  public void logout(UserInterface callingUI) {
    BenutzerVw.logout(callingUI);
  }

  // #endregion
  // #region Warenkorb

  public HashMap<Artikel, Integer> WK_getInhalt() {
    return WarenkorbVw.getInhalt();
  }

  public Warenkorb WV_getWarenkorb() {
    return WarenkorbVw.getWarenkorb();
  }

  public void WV_setArtikel(Artikel artikel, int integer) {
    WarenkorbVw.setArtikel(artikel, integer);
  }

  public void WV_removeArtikel(Artikel artikel) {
    WarenkorbVw.removeArtikel(artikel);
  }

  public void WV_clearAll() {
    WarenkorbVw.clearAll();
  }

  public Rechnung WV_kaufen(byte[] userHash) throws ExceptionArtikelCollection {

    // BenutzerVw.sucheMitarbeiter(userNumber)
    Benutzer thisuser = BV_getUserActive(userHash);
    Rechnung rechnung = WarenkorbVw.ArtikelKaufen(
        thisuser,
        BenutzerVw.getDataNummer(thisuser),
        BenutzerVw.getDataName(thisuser));

    // setz neuen Bestand
    rechnung.getInhalt().forEach((artikel, anzahl) -> {
      try {
        AV_setArtikel(userHash, artikel, ArtikelVw.getArtikelBestand(artikel) - anzahl);
      } catch (ExceptionArtikelUngültigerBestand e) {
        e.printStackTrace();
      }
    });

    // duzende Artikel.änderungs events
    return rechnung;
  }

  public double WV_getSumme() {
    return WarenkorbVw.gesamtSumme();
  }

  // #endregion Warenkorb
  // #region Artikelvw

  public Artikel AV_addArtikel(byte[] userHash, String name, int bestand, double einzelpreis, int packungsInhalt)
      throws ExceptionArtikelExistiertBereits, ExceptionArtikelKonnteNichtErstelltWerden {

    Artikel artikel = ArtikelVw.addArtikel(name, bestand, einzelpreis, packungsInhalt);
    // ereignis loggen
    // TODO: EVENT - event basiert

    EV_EreignisArtikelCreate(userHash, "Artikel Erstellen", artikel);

    return artikel;
  }

  public void AV_deleteArtikel(byte[] userHash, String name) throws ExceptionArtikelKonnteNichtGelöschtWerden {

    Artikel artikel;
    try {
      artikel = ArtikelVw.findArtikelByName(name);

      ArtikelVw.deleteArtikel(artikel);
      EV_EreignisArtikelDelete(userHash, "Artikel Löschen", artikel);
    } catch (ExceptionArtikelNichtGefunden e) {
      throw new ExceptionArtikelKonnteNichtGelöschtWerden(e);
    }
  }

  // #region set artikel

  public void AV_setArtikel(byte[] userHash, Artikel artikel, String neuerName)
      throws ExceptionArtikelNameExistiertBereits, ExceptionArtikelNameUngültig {
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
   * @throws ExceptionArtikelNameUngültig
   */
  public void AV_setArtikel(byte[] userHash, String name, String neuerName)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelNameExistiertBereits, ExceptionArtikelNameUngültig {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), neuerName);
  }

  public void AV_setArtikel(byte[] userHash, Artikel artikel, int bestand) throws ExceptionArtikelUngültigerBestand {

    if (artikel != null) {
      int bestandAlt = ArtikelVw.getArtikelBestand(artikel);
      // set neuer bestand
      ArtikelVw.setArtikelBestand(artikel, bestand);
      // logt neuen und alten bestand
      EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, null, bestandAlt, null);
    }
  }

  public void AV_setArtikel(byte[] userHash, String name, int bestand)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), bestand);
  }

  public void AV_setArtikel(byte[] userHash, Artikel artikel, double preis) {

    double preisAlt = ArtikelVw.getArtikelPreis(artikel);
    // set neuer preis
    ArtikelVw.setArtikelPreis(artikel, preis);
    // logt neuen und alten preis
    EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, null, null, preisAlt);
  }

  public void AV_setArtikel(byte[] userHash, String name, double preis)
      throws ExceptionArtikelNichtGefunden {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), preis);
  }

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
        e.printStackTrace();
        System.out.println("hihu");
      }

      // logt neuen und alten preis
      EV_EreignisArtikelData(userHash, "Artikel Bestand änderung", artikel, nameAlt, bestandAlt, preisAlt);
    }
  }

  public void AV_setArtikel(byte[] userHash, String name, String neuerName, int bestand, double preis)
      throws ExceptionArtikelNichtGefunden, ExceptionArtikelUngültigerBestand {
    AV_setArtikel(userHash, ArtikelVw.findArtikelByName(name), neuerName, bestand, preis);
  }

  // #endregion

  public Artikel AV_findArtikelByName(String name) throws ExceptionArtikelNichtGefunden {
    return ArtikelVw.findArtikelByName(name);
  }

  // #region Artikelvw darstellung

  public Vector<Artikel> AV_getAlleArtikelList() {
    return ArtikelVw.getAlleArtikelList();
  }

  public String AV_ArtikelAusgeben(Vector<Artikel> list, boolean detailed, String leereNachicht) {
    return ArtikelVw.displayArtikel(list, detailed, leereNachicht);
  }

  /**
   * liste ausgeben
   * 
   * @param ordnung
   * @param detailed
   * @param leereNachicht
   * @return
   */
  public String AV_ArtikelAusgeben(SuchOrdnung ordnung, boolean detailed, String leereNachicht) {
    return ArtikelVw.displayArtikel(ordnung, detailed, leereNachicht);
  }

  public SuchOrdnung AV_sucheArtikel(String searchTerm) {
    return ArtikelVw.suchArtikel(searchTerm);
  }

  // sort

  public void AV_sortListName(SuchOrdnung ordnung, boolean reverse) {
    ArtikelVw.sortListName(ordnung, reverse);
  }

  public void AV_sortListName(Vector<Artikel> artikelList, boolean reverse) {
    ArtikelVw.sortListName(artikelList, reverse);
  }

  public void AV_sortListPreis(SuchOrdnung ordnung, boolean reverse) {
    ArtikelVw.sortListPreis(ordnung, reverse);
  }

  public void AV_sortListPreis(Vector<Artikel> artikelList, boolean reverse) {
    ArtikelVw.sortListPreis(artikelList, reverse);
  }

  public void AV_sortListRelevanz(SuchOrdnung ordnung) {
    ArtikelVw.sortListRelevanz(ordnung);
  }

  // #endregion Artikelvw darstellung

  // #endregion Artikel
  // #region Ereignis Log /////////////////////////////////////////

  public String EV_logDisplay() {
    return EreignisVw.displayLog(false);
  }

  public String EV_ereignisDisplay(boolean detailed, int ereignisNummer) throws ExceptionEreignisNichtGefunden {
    return EreignisVw.displayEreignis(detailed, ereignisNummer);
  }

  public Ereignis EV_getEreignis(int ereignisNummer) throws ExceptionEreignisNichtGefunden {
    return EreignisVw.findeEreignis(ereignisNummer);
  }

  public Vector<EreignisArtikel> EV_getArtikelEreignis(Artikel artikel) {
    return EreignisVw.getArtikelEreignis(artikel);
  }

  public Integer[] EV_getBestandsHistore(Artikel artikel) {
    return EreignisVw.getBestandHistory(artikel);
  }

  public Vector<Ereignis> EV_getLog() {
    return EreignisVw.getLog();
  }

  public SuchOrdnung EV_sucheEreignisse(String searchterm) {
    return EreignisVw.suchEvent(searchterm);
  }

  // #region neue ereignisse /////////////////////////////////////////

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

  // #endregion ////////////////////////////////////////////////

  @Override
  public void quit() {
    // TODO QUIIIIIIIIIIIT
    saveData();
  }

  private void saveData() {
    try {
      BenutzerVw.save();
      ArtikelVw.save();
      EreignisVw.save();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public UserInterface createUserInterface() {

    return new CUI(this);
  }

  public static void main(String[] args) {
    System.out.println("/////////////SERVER/////////////");
    int port = 0;
    if (args.length == 1) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        port = 0;
      }
    }

    Eshop eshop = new Eshop("", 0);

    SocketVerwaltung SV = new SocketVerwaltung(eshop, port);
    SV.acceptClientConnectRequests();
  }

}
