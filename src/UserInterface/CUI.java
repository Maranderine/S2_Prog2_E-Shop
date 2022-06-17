package UserInterface;

import java.util.Stack;

import Domain.Eshop;
import Domain.Artikel.Artikel;
import Domain.Warenkorb.Rechnung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Input.ExceptionInputExit;
import Exceptions.Input.ExceptionInputFalsch;

public class CUI extends UserInterface {

  public CUI(Eshop eshop) {
    super(eshop);
    // move level to start menu
    LevelMove(startLevel);
  }

  /** Display and process of the CUI menu */
  @SuppressWarnings("unused")
  public boolean run() {
    // TODO TEST
    // regexChecker(PatternText);

    String keinnav = "Eingabe existiert nicht in dieser Navigation vorhanden";
    int num;
    String string;
    String string2;
    String string3;
    Artikel artikel = null;
    Artikel artikelDetailAnsicht = null;

    // print top
    System.out.println("/////////////////////////////////////////////");
    // first switch determines the menu level, the menu that should be displayed
    switch (Level()) {
      case MAIN_MENU:
        // #region MAIN_MENU
        System.out.println("____________E-Shop___________");
        System.out.println("1 = Anmelden");
        System.out.println("2 - Registrieren");
        System.out.println("0 = Exit");

        try {
          string = GetInputNavigation(0, " > ", null);

          switch (string) {
            case "1":// Anmelden
              // #region Anmelden
              System.out.println("---------LOGIN---------");
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

        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          // Exception
          System.out.println(e.getMessage());
        }

        break;
      // #endregion MAIN_MENU
      case KUNDEN_REGISTRIEREN:
        // #region KUNDEN_REGISTRIEREN
        try {
          System.out.println("__________Kunde Registrieren_________");
          String name;

          name = GetInputNamen(0, "Vor und Nachname: >", "exit");
          String email = GetInputEmail(0, "Email: >", "exit");
          System.out.println("Addresse:");
          String address = GetInputAdresse(0, "exit");
          String un = GetInputUserNameNew(0, "User Name: >", "exit");
          String passwort = GetInputPasswortNew(0, "Passwort: >", "exit");
          eshop.BV_kundeHinzufügen(name, un, passwort, email, address);

        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          System.out.println(e.getMessage());
        }
        LevelReturn();
        break;
      // #endregion KUNDEN_REGISTRIEREN
      case KUNDEN_ANSICHT:
        // #region KUNDEN_ANSICHT
        System.out.println("____________KUNDE____________");
        System.out.println("1 = alle Artikel ausgeben");
        System.out.println("2 = Artikel suchen");
        System.out.println("3 = Artikel ispizieren");
        System.out.println("4 = Artikel dem Warenkorb hinzufügen");
        System.out.println("5 = Warenkorb");
        System.out.println("0 = Logout");

        try {
          string = GetInputNavigation(0, " > ", null);

          switch (string) {

            case "1":// Alle Artikel ausgeben
              System.out.println(eshop.AV_alleArtikel());
              break;
            case "2":// artikel suchen
              String searchTerm = GetInputText(0, "Suchen nach  > ", "exit");
              System.out.println(eshop.AV_sucheArtikel(searchTerm));
              break;
            case "3":// artikel ispizieren
              String titel = GetInputArtikel(1, "Artikel Name  > ", null);
              try {
                System.out.println(eshop.AV_findArtikelByName(titel).toStringDetailled());
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e.getMessage());
              }
              break;

            case "4":// artikel in den Warenkorb
              try {
                artikel = eshop.AV_findArtikelByName(GetInputArtikel(1, "Artikel Name  > ", null));
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e.getMessage());
              }
              num = Integer.parseInt(GetInputNum(0, "Anzahl  > ", "exit"));

              eshop.WV_setArtikel(artikel, num);
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
          System.out.println(e.getMessage());
        }

        break;
      // #endregion KUNDEN_ANSICHT
      case WARENKORB:
        // #region WARENKORB
        System.out.println("____________________Warenkorb_____________________");
        System.out.println(eshop.WV_getWarenkorb());// display warenkorb
        System.out.println("--------------------------------------------------");
        System.out.println("\t1 = Artikel anzahl ändern");
        System.out.println("\t2 = Einzelnden Artikel löschen");
        System.out.println("\t3 = Alle Artikel löschen");
        System.out.println("\t4 = KAUFEN");
        System.out.println("\t0 = Exit");

        try {
          string = GetInputNavigation(0, " > ", null);

          switch (string) {
            case "1":// ändern
              System.out.println("------Artikel anzahl ändern------");
              try {
                artikel = eshop.AV_findArtikelByName(GetInputArtikel(0, "Artikel Name > ", "exit"));
                num = Integer.parseInt(GetInputNum(0, "Artikel anzahl > ", "exit"));

                eshop.WV_setArtikel(artikel, num);
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e.getMessage());
              }
              break;
            case "2":// löschen
              System.out.println("------Einzelnden Artikel löschen------");
              try {
                artikel = eshop.AV_findArtikelByName(GetInputArtikel(0, "Artikel Name > ", "exit"));
                eshop.WV_removeArtikel(artikel);
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e.getMessage());
              }
              break;
            case "3":// Alle löschen
              eshop.WV_clearAll();
              break;
            case "4":// KAUFEN
              System.out.println("------ KAUF BESTÄTIGUNG ------");
              System.out.println("\tKaufbestätigung:");
              System.out.println("\ty = ja kaufen");
              System.out.println("\tn = nein zurück");
              System.out.print(" >");

              string = GetInput();

              if (string.equals("y")) {
                Rechnung rechnung;
                try {
                  rechnung = eshop.WV_kaufen(this.userHash);
                  System.out.print(rechnung);
                } catch (ExceptionArtikelCollection e) {
                  // TODO runcatch
                  System.out.println("Kaufen Fehl Geschlagen!");
                  System.out.println(e.getMessage());
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
          System.out.println(e.getMessage());
        }
        break;
      // #endregion
      case MITARBEITER_ANSICHT:
        // #region MITARBEITER_ANSICHT
        System.out.println("____________MITARBEITER____________");
        System.out.println("1 = Artikel Verwalten");
        System.out.println("2 = Mitarbeiter hinzufügen");
        System.out.println("3 = Ereignis Log");
        System.out.println("0 = Logout");

        try {
          string = GetInputNavigation(0, " > ", null);

          switch (string) {
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
        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          System.out.println(e.getMessage());
        }
        break;
      // #endregion MITARBEITER_ANSICHT
      case MITARBEITER_ARTIKEL:
        // #region MITARBEITER_ARTIKEL
        System.out.println("Artikel Verwalten");
        System.out.println(eshop.AV_alleArtikel());
        System.out.println("1 = Artikel Detail Ansicht");
        System.out.println("2 = Artikel löschen");
        System.out.println("3 = Artikel hinzufügen");
        System.out.println("4 = Artikel Bestand ändern");
        System.out.println("0 = Exit");

        try {
          string = GetInputNavigation(0, " > ", null);

          switch (string) {
            case "1":// detail ansicht artikel
              System.out.println();
              try {
                artikelDetailAnsicht = eshop.AV_findArtikelByName(GetInputArtikel(0, "Artikel Name > ", "exit"));
                LevelMove(MenuLevel.ARTIKELDETAILANSICHT);
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e.getMessage());
              }
              break;
            case "2":// Artikel löschen
              string = GetInputArtikel(0, "zu löschender Artikel > ", "exit");
              try {
                eshop.AV_deleteArtikel(this.userHash, string);
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e.getMessage());
              }
              break;
            case "3":// Artikel hinzufügen
              System.out.println("Neuer Artikel");
              String artikelName = GetInputArtikel(0, "Artikel Name > ", "exit");
              int bestand = Integer.parseInt(GetInputNum(0, "Artikel Bestand > ", "exit"));
              double preis = Double.parseDouble(GetInputFloat(0, "Artikel Preis > ", "exit"));

              Artikel art;
              try {
                eshop.AV_addArtikel(this.userHash, artikelName, bestand, preis);
                System.out.println("Erfolgreich erstellt!");
              } catch (ExceptionArtikelExistiertBereits e) {
                System.out.println("Etwas ist schief gelaufen.");
                // TODO runcatch
                System.out.println(e.getMessage());
              }

              break;
            case "4":// Artikel Bestand ändern
              // TODO wieso gab es das nicht??? xD

              break;
            case "0":
              LevelReturn();
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionInputFalsch | ExceptionInputExit e) {
          System.out.println(e.getMessage());
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
          System.out.println(e.getMessage());
        }
        LevelReturn();

        break;

      // #endregion MITARBEITER_REGISTRIEREN
      case MITARBEITER_EREIGNISLOG:
        // #region MITARBEITER_EREIGNISLOG
        System.out.println("____________Ereignis Log____________");
        System.out.print(eshop.EV_logDisplay());
        System.out.println("------------------------------------");
        System.out.println("1 = ");
        System.out.println("2 = Detail ansicht");
        System.out.println("3 = ");
        System.out.println("0 = Exit");

        try {
          string = GetInputNavigation(0, " > ", null);

          switch (string) {
            case "1"://
              // #region
              // #endregion
              break;
            case "2":// Detail ansicht
              // #region
              try {
                artikelDetailAnsicht = eshop.AV_findArtikelByName(GetInputArtikel(0, "Artikel Name > ", "exit"));
                LevelMove(MenuLevel.ARTIKELDETAILANSICHT);
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e.getMessage());
              }
              // #endregion
              break;
            case "3"://
              // #region
              // #endregion
              break;
            case "0":// Exit
              // #region
              LevelReturn();
              // #endregion
              break;
            default:
              System.out.println(keinnav);
          }
        } catch (ExceptionInputFalsch | ExceptionInputExit e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }

        break;
      // #endregion MITARBEITER_EREIGNISLOG
      case ARTIKELDETAILANSICHT:
        // #region

        if (artikelDetailAnsicht != null) {
          // THIS IS NOT DEAD CODE >:(
          System.out.println("____________Artikel Detaille ansicht____________");
          System.out.print(artikelDetailAnsicht.toStringDetailled());
          System.out.println("------------------------------------");
          System.out.println("0 = Exit");

          try {
            string = GetInputNavigation(0, " > ", null);
            switch (string) {
              case "0":// Exit
                // #region
                LevelReturn();
                // #endregion
                break;
            }

          } catch (ExceptionInputFalsch | ExceptionInputExit e) {
            System.out.println(e.getMessage());
          }
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
    ARTIKELDETAILANSICHT
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
    LevelReset();
  }
}