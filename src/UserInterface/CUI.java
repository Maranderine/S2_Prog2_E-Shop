package UserInterface;

import java.util.Stack;

import Domain.Eshop;
import Domain.Artikel.Artikel;
import Domain.EreignisLog.Ereignisse.Ereignis;
import Domain.EreignisLog.Interfaces.EreignisInterface_CallingBenutzer;
import Domain.Search.SuchOrdnung;
import Domain.Warenkorb.Rechnung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNameExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Artikel.ExceptionArtikelUngültigerBestand;
import Exceptions.Ereignis.ExceptionEreignisNichtGefunden;
import Exceptions.Input.ExceptionInputExit;
import Exceptions.Input.ExceptionInputFalsch;

public class CUI extends UserInterface {

  public CUI(Eshop eshop) {
    super(eshop);
    // move level to start menu
    LevelMove(startLevel);
  }

  /** Display and process of the CUI menu */
  // @SuppressWarnings("unused")

  Artikel artikelDetailAnsicht = null;
  Ereignis ereignisDetailAnsicht = null;
  Artikel artikel_bearbeiten = null;
  SuchOrdnung suchOrdnung = null;
  String searchTerm = null;

  public boolean run() {
    // TODO TEST
    // regexChecker(PatternText);

    String keinnav = "\nEingabe existiert nicht in dieser Navigation vorhanden\n";
    int num;
    String string;
    Artikel artikel = null;

    // print top
    // System.out.println("\n/////////////////////////////////////////////");
    // first switch determines the menu level, the menu that should be displayed
    switch (Level()) {
      case MAIN_MENU:
        // #region MAIN_MENU
        System.out.print("\n____________E-Shop___________\n\n");
        System.out.println("1 = Anmelden");
        System.out.println("2 = Registrieren");
        System.out.println("0 = Exit");

        try {
          switch (GetInputNavigation("\n > ")) {
            case "1":// Anmelden
              // #region Anmelden
              System.out.print("\n---------LOGIN---------\n\n");
              String username = GetInputUserNameAbfrage("username > ");
              String password = GetInputPasswortAbfrage("password  > ");

              // Benutzerverwaltung.BeutzerType nutzer = eshop.login(username,
              // password);
              switch (eshop.login(this, username, password)) {
                case NONE:
                  System.out.println(" Benutzername oder Passwort falsch");
                  break;
                case MITARBEITER:
                  LevelMove(MenuLevel.MITARBEITER_ANSICHT);
                  break;
                case KUNDE:
                  LevelMove(MenuLevel.KUNDEN_ANSICHT);
                  break;
              }
              break;
            // #endregion
            case "2":// Registrieren
              LevelMove(MenuLevel.KUNDEN_REGISTRIEREN);
              break;
            case "0":// Exit
              eshop.saveData();
              return false;
            default:
              System.out.println(keinnav);

          }

        } catch (ExceptionInputFalsch e) {
          runCatch(e);
        }

        break;
      // #endregion MAIN_MENU
      case KUNDEN_REGISTRIEREN:
        // #region KUNDEN_REGISTRIEREN
        try {
          System.out.print("\n__________Kunde Registrieren_________\n\n");

          System.out.print("\n");
          String name = GetInputNamen(0, "Vor und Nachname: > ", "exit");
          System.out.print("\n");
          String email = GetInputEmail(0, "Email: > ", "exit");
          System.out.print("\n");
          String address = GetInputAdresse(0, "Addresse:", "exit", "\t");
          System.out.print("\n");
          String un = GetInputUserNameNew(0, "User Name: > ", "exit");
          System.out.print("\n");
          String passwort = GetInputPasswortNew(0, "Passwort: > ", "exit");

          eshop.BV_kundeHinzufügen(name, un, passwort, email, address);

        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          runCatch(e);
        }
        LevelReturn();
        break;
      // #endregion KUNDEN_REGISTRIEREN
      case KUNDEN_ANSICHT:
        // #region KUNDEN_ANSICHT
        System.out.print("\n____________KUNDE____________\n\n");

        if (suchOrdnung == null) {
          System.out.println("Alle Artikel:");
          System.out.print(eshop.AV_alleArtikelAusgeben(false) + "\n");
        } else {
          System.out.println("Gesuchte Artikel: \"" + this.searchTerm + "\"");
          System.out.println("Artikelnr | Name | Preis");
          System.out.print(this.suchOrdnung.display(false, "Keine Artikel Gefunden") + "\n\n");
        }

        System.out.println("1 = Alle Artikel ausgeben");
        System.out.println("2 = Artikel suchen");
        System.out.println("3 = Artikel ispizieren");
        System.out.println("4 = Einen Artikel dem Warenkorb hinzufügen");
        System.out.println("5 = Warenkorb Anzeigen");
        System.out.println("0 = Logout");

        try {
          switch (GetInputNavigation("\n > ")) {
            case "1":// alle artikel
              this.searchTerm = null;
              this.suchOrdnung = null;
              break;
            case "2":// artikel suchen
              System.out.print("\n");
              this.searchTerm = GetInputText(0, "Suchen nach > ", "exit");
              this.suchOrdnung = eshop.AV_sucheArtikel(searchTerm);
              break;
            case "3":// artikel ispizieren
              System.out.print("\n");
              String titel = GetInputArtikel(1, "Artikel Name  > ", null);
              try {
                string = eshop.AV_findArtikelByName(titel).toStringDetailed();
                System.out.print("\nArtikelnr | Name | Bestand | Preis\n\n" + string);
              } catch (ExceptionArtikelNichtGefunden e) {
                runCatch(e);
              }
              break;
            case "4":// artikel in den Warenkorb
              try {
                System.out.print("\n");
                artikel = eshop.AV_findArtikelByName(GetInputArtikel(1, "Artikel Name  > ", "exit"));
                System.out.print("\n");
                num = Integer.parseInt(GetInputNum(0, "Anzahl  > ", "exit"));
                eshop.WV_setArtikel(artikel, num);
                System.out.print("\nHinzugefügt!\n");
              } catch (ExceptionArtikelNichtGefunden e) {
                runCatch(e);
              }
              break;
            case "5":// Warenkorb
              LevelMove(MenuLevel.WARENKORB);
              break;
            case "0":// Exit
              Logout();
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          runCatch(e);
        }

        break;
      // #endregion KUNDEN_ANSICHT
      case WARENKORB:
        // #region WARENKORB
        System.out.print("\n____________________Warenkorb_____________________\n\n");
        System.out.println(eshop.WV_getWarenkorb());// display warenkorb
        System.out.print("\n--------------------------------------------------\n\n");
        System.out.println("\t1 = Artikel anzahl ändern");
        System.out.println("\t2 = Einzelnden Artikel löschen");
        System.out.println("\t3 = Alle Artikel löschen");
        System.out.println("\t4 = KAUFEN");
        System.out.println("\t0 = Exit");

        try {
          switch (GetInputNavigation("\n > ")) {
            case "1":// ändern
              System.out.print("\nArtikel anzahl ändern\n\n");
              try {
                artikel = eshop.AV_findArtikelByName(GetInputArtikel(0, "Artikel Name > ", "exit"));
                num = Integer.parseInt(GetInputNum(0, "Artikel anzahl > ", "exit"));

                eshop.WV_setArtikel(artikel, num);
              } catch (ExceptionArtikelNichtGefunden e) {
                runCatch(e);
              }
              break;
            case "2":// löschen
              System.out.println("\nEinzelnden Artikel löschen\n\n");
              try {
                artikel = eshop.AV_findArtikelByName(GetInputArtikel(0, "Artikel Name > ", "exit"));
                eshop.WV_removeArtikel(artikel);
              } catch (ExceptionArtikelNichtGefunden e) {
                runCatch(e);
              }
              break;
            case "3":// Alle löschen
              eshop.WV_clearAll();
              break;
            case "4":// KAUFEN
              System.out.print("\n------ KAUF BESTÄTIGUNG ------\n\n");
              System.out.println("\tKaufbestätigung:");
              System.out.println("\ty = ja kaufen");
              System.out.println("\tn = nein zurück");
              System.out.print("\n > ");

              string = GetInput();

              if (string.equals("y")) {
                Rechnung rechnung;
                try {
                  rechnung = eshop.WV_kaufen(this.userHash);
                  System.out.print(rechnung);
                } catch (ExceptionArtikelCollection e) {
                  System.out.println("Kaufen Fehl Geschlagen!");
                }
              }

              break;
            case "0":// Exit
              LevelReturn();
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          runCatch(e);
        }
        break;
      // #endregion
      case MITARBEITER_ANSICHT:
        // #region MITARBEITER_ANSICHT
        System.out.print("\n____________MITARBEITER____________\n\n");
        System.out.println("1 = Artikel Verwalten");
        System.out.println("2 = Mitarbeiter hinzufügen");
        System.out.println("3 = Ereignis Log");
        System.out.println("0 = Logout");

        try {
          switch (GetInputNavigation("\n > ")) {
            case "1":// artikel Verwalten
              LevelMove(MenuLevel.MITARBEITER_ARTIKEL);
              break;
            case "2":// Mitarbeiter hinzufügen
              LevelMove(MenuLevel.MITARBEITER_REGISTRIEREN);
              break;
            case "3":// Mitarbeiter hinzufügen
              LevelMove(MenuLevel.MITARBEITER_EREIGNISLOG);
              break;
            case "0":// logout
              Logout();
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionInputFalsch e) {
          runCatch(e);
        }
        break;
      // #endregion MITARBEITER_ANSICHT
      case MITARBEITER_ARTIKEL:
        // #region MITARBEITER_ARTIKEL
        System.out.print("\n________Artikel_Verwalten________\n\n");
        System.out.println(eshop.AV_alleArtikelAusgeben(true));
        System.out.println("1 = Artikel Detail Ansicht");
        System.out.println("2 = Artikel löschen");
        System.out.println("3 = Artikel hinzufügen");
        System.out.println("4 = Artikel bearbeiten");
        System.out.println("0 = Exit");

        try {
          switch (GetInputNavigation("\n > ")) {
            case "1":// detail ansicht artikel
              LevelMove(MenuLevel.ARTIKEL_DETAILANSICHT);
              break;
            case "2":// Artikel löschen
              string = GetInputArtikel(0, "zu löschender Artikel > ", "exit");
              try {
                eshop.AV_deleteArtikel(this.userHash, string);
              } catch (ExceptionArtikelNichtGefunden e) {
                runCatch(e);
              }
              break;
            case "3":// Artikel hinzufügen
              System.out.println("Neuer Artikel");
              String artikelName = GetInputArtikel(0, "Artikel Name > ", "exit");
              int bestand = Integer.parseInt(GetInputNum(0, "Artikel Bestand > ", "exit"));
              double preis = Double.parseDouble(GetInputFloat(0, "Artikel Preis > ", "exit"));

              try {
                eshop.AV_addArtikel(this.userHash, artikelName, bestand, preis);
                System.out.println("Erfolgreich erstellt!");
              } catch (ExceptionArtikelExistiertBereits e) {
                System.out.println("Etwas ist schief gelaufen.");
                runCatch(e);
              }

              break;
            case "4":// Artikel Bearbeiten

              artikel = null;
              LevelMove(MenuLevel.ARTIKEL_BEARBEITEN);
              // try {
              // string = GetInputArtikel(0, "Artikel > ", "exit");
              // artikel = eshop.AV_findArtikelByName(string);
              // LevelMove(MenuLevel.ARTIKEL_BEARBEITEN);
              // } catch (ExceptionArtikelNichtGefunden e) {
              // runCatch(e);
              // }

              break;
            case "0":
              LevelReturn();
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          runCatch(e);
        }
        break;
      // #endregion MITARBEITER_ARTIKEL
      case MITARBEITER_REGISTRIEREN:
        // #region MITARBEITER_REGISTRIEREN
        try {
          System.out.println("__________Mitarbeiter Registrieren_________");
          String nam;

          nam = GetInputNamen(0, " Vor und Nachname: > ", "exit");
          String user = GetInputUserNameNew(0, " username: > ", "exit");
          String pass = GetInputPasswortNew(0, " passwort: > ", "exit");
          eshop.BV_mitarbeiterHinzufügen(nam, user, pass);
        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          runCatch(e);
        }
        LevelReturn();

        break;

      // #endregion MITARBEITER_REGISTRIEREN
      case MITARBEITER_EREIGNISLOG:
        // #region MITARBEITER_EREIGNISLOG
        System.out.print("\n____________Ereignis Log____________\n\n");
        System.out.print(eshop.EV_logDisplay());
        System.out.print("----------------------------------------------------------------------\n\n");
        System.out.println("1 = Suchen");
        System.out.println("2 = Detail ansicht Event");
        System.out.println("3 = Detail ansicht Artikel");
        System.out.println("0 = Exit");

        try {
          switch (GetInputNavigation("\n > ")) {
            case "1"://

              break;
            case "2":// Detail ansicht Event
              LevelMove(MenuLevel.EREIGNIS_DETAILANSICHT);
              break;
            case "3":// Detail ansicht Artikel
              LevelMove(MenuLevel.ARTIKEL_DETAILANSICHT);
              break;
            case "0":// Exit
              // #region
              LevelReturn();
              // #endregion
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionInputFalsch e) {
          runCatch(e);
        }

        break;
      // #endregion MITARBEITER_EREIGNISLOG
      // #region
      case EREIGNIS_DETAILANSICHT:

        if (ereignisDetailAnsicht == null) {
          try {

            ereignisDetailAnsicht = eshop
                .EV_getEreignis(Integer.parseInt(GetInputNum(0, "Ereignis Nummer > ", "exit")));
          } catch (NumberFormatException e) {
            e.printStackTrace();
          } catch (ExceptionInputFalsch | ExceptionEreignisNichtGefunden e) {
            runCatch(e);
          } catch (ExceptionInputExit e) {
            LevelReturn();
          }
        } else
          try {
            System.out.print("\n_________Ereignis_Detaile_Ansicht_________\n\n");
            System.out.print(ereignisDetailAnsicht.toStringDetailed());
            System.out.print("\n----------------------------------------------------------------------\n\n");

            System.out.println("1 = Anderes Ereignis");

            // TODO get implemented interfaces and give different options depending
            if (ereignisDetailAnsicht instanceof EreignisInterface_CallingBenutzer) {
              System.out.println("benuzzzzzeeeeeer");
            }

            System.out.println("2 = Detail ansicht Event");
            System.out.println("3 = Detail ansicht Artikel");
            System.out.println("0 = Exit");

            switch (GetInputNavigation("\n > ")) {
              case "1"://
                ereignisDetailAnsicht = null;
                break;
              case "2":// Detail ansicht Event
                LevelMove(MenuLevel.EREIGNIS_DETAILANSICHT);
                break;
              case "3":// Detail ansicht Artikel
                LevelMove(MenuLevel.ARTIKEL_DETAILANSICHT);
                break;
              case "0":// Exit
                ereignisDetailAnsicht = null;
                LevelReturn();
                break;
              default:
                System.out.println(keinnav);
            }
          } catch (ExceptionInputFalsch e) {
            runCatch(e);
          }
        break;
      // #endregion
      case ARTIKEL_DETAILANSICHT:
        // #region
        try {
          if (artikelDetailAnsicht == null)
            try {
              System.out.print("\n");
              artikelDetailAnsicht = eshop.AV_findArtikelByName(GetInputArtikel(0, "Artikel Name > ", "exit"));
            } catch (ExceptionInputExit e) {
              LevelReturn();
              throw e;
            }

          System.out.print("\n____________Artikel_Detail_ansicht____________\n\n");
          System.out.print(artikelDetailAnsicht.toStringDetailed());
          System.out.println("\n------------------------------------\n");
          // TODO display events relating to Artikel

          System.out.println("1 = Artikel Bearbeiten");
          System.out.println("2 = Artikel wechseln");
          System.out.println("0 = Exit");

          switch (GetInputNavigation("\n > ")) {
            case "1":// Bearbeiten
              // set aktueller detail artikel als zu bearbeitender artikel
              artikel_bearbeiten = artikelDetailAnsicht;
              LevelMove(MenuLevel.ARTIKEL_BEARBEITEN);
              break;
            case "2":// wechseln
              artikelDetailAnsicht = null;
              break;
            case "0":// Exit
              artikelDetailAnsicht = null;
              LevelReturn();
              break;
            default:
              System.out.println(keinnav);
          }

        } catch (ExceptionArtikelNichtGefunden | ExceptionInputFalsch | ExceptionInputExit e) {
          runCatch(e);
        }

        break;
      // #endregion
      case ARTIKEL_BEARBEITEN:
        // #region

        try {
          // gette artikel zu bearbeiten und gib möglichkeit ins vorherige menu zurück zu
          // kehren
          if (artikel_bearbeiten == null)
            try {
              System.out.print("\n");
              string = GetInputArtikel(0, "Artikel > ", "exit");
              artikel_bearbeiten = eshop.AV_findArtikelByName(string);
            } catch (ExceptionInputExit e) {
              // if exit was used in the Artikel find
              LevelReturn();// return to previous level
              throw e;// skip to catch
            }

          System.out.print("\n/////////// " + artikel_bearbeiten.getName() + ": Artikel Bearbeiten ///////////\n\n");
          System.out.print(artikel_bearbeiten.toStringDetailed() + "\n\n");
          System.out.println("1 = Name bearbeiten");
          System.out.println("2 = Bestand bearbeiten");
          System.out.println("3 = Preis bearbeiten");
          System.out.println("0 = Exit");

          switch (GetInputNavigation("\n > ")) {
            case "1":// Name
              System.out.println("\nAktueller Name:\t" + artikel_bearbeiten.getName());
              eshop.AV_setArtikel(this.userHash, artikel_bearbeiten, GetInputArtikel(0, "neuer Name > ", "exit"));
              System.out.print("\nÄnderung Übernommen\n\n");
              break;
            case "2":// Bestand
              System.out.println("\nAktueller Bestand:\t" + artikel_bearbeiten.getBestand());
              eshop.AV_setArtikel(this.userHash, artikel_bearbeiten,
                  Integer.parseInt(GetInputNum(0, "neuer Bestand > ", "exit")));
              System.out.print("\nÄnderung Übernommen\n\n");
              break;
            case "3":// Preis
              System.out.println("\nAktueller Preis:\t" + artikel_bearbeiten.getPreis());
              eshop.AV_setArtikel(this.userHash, artikel_bearbeiten,
                  Double.parseDouble(GetInputFloat(0, "neuer Preis > ", "exit")));
              System.out.print("\nÄnderung Übernommen\n\n");
              break;
            case "0":
              artikel_bearbeiten = null;
              LevelReturn();
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionArtikelNichtGefunden | NumberFormatException | ExceptionInputFalsch | ExceptionInputExit
            | ExceptionArtikelUngültigerBestand | ExceptionArtikelNameExistiertBereits e) {
          runCatch(e);
        }
        break;
      // #endregion
    }
    // true to continue
    return true;
  }

  // #region level system ///////////////////////

  // menu level enum and value
  enum MenuLevel {
    MAIN_MENU, // start menu
    KUNDEN_REGISTRIEREN,
    KUNDEN_ANSICHT,
    WARENKORB,
    MITARBEITER_ANSICHT,
    MITARBEITER_ARTIKEL,
    MITARBEITER_REGISTRIEREN,
    MITARBEITER_EREIGNISLOG,
    EREIGNIS_DETAILANSICHT,
    ARTIKEL_DETAILANSICHT,
    ARTIKEL_BEARBEITEN
  }

  /** the start level, displayed on statup */
  static MenuLevel startLevel = MenuLevel.MAIN_MENU;
  /** Keeps track of current and all past menulevels */
  private static Stack<MenuLevel> levelStack = new Stack<MenuLevel>();

  /**
   * Changes the current menulevel
   * remembers all past levels
   * 
   * @param level to be moved to
   */
  private static void LevelMove(MenuLevel level) {
    levelStack.push(level);
  }

  /**
   * returns to the level previous
   * 
   * @return now the current level
   */
  private static MenuLevel LevelReturn() {
    if (levelStack.capacity() == 1)
      return startLevel;

    return levelStack.pop();
  }

  /**
   * return current level
   * 
   * @return currently in use
   */
  private static MenuLevel Level() {
    return levelStack.peek();
  }

  /**
   * resets level to start value
   * 
   * @return
   */
  private static MenuLevel LevelReset() {
    levelStack.clear();
    levelStack.push(startLevel);
    return startLevel;
  }

  // #endregion ///////////////////////

  /**
   * logs the user out and LevelReturn()
   */
  private void Logout() {
    eshop.logout(this);
    System.out.print("\n---- loggeed out ----\n\n");
    LevelReset();
  }
}