package UserInterface;

import java.util.Stack;

import Domain.Eshop;
import Domain.Artikel.Artikel;
import Domain.Warenkorb.Rechnung;
import Exceptions.Artikel.ExceptionArtikelCollection;
import Exceptions.Artikel.ExceptionArtikelExistiertBereits;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
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
    // regexChecker();

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
          string = GetInput(1, "\t> ", null);

          switch (string) {
            case "1":// Anmelden
              // #region Anmelden
              System.out.println("LOGIN");
              System.out.print("username > ");
              String username = GetInput();

              System.out.print("password  > ");
              String password = GetInput();

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
          }

        } catch (ExceptionInputFalsch e) {
          // Exception
          System.out.println(e);
        }

        break;
      // #endregion MAIN_MENU
      case KUNDEN_REGISTRIEREN:
        // #region KUNDEN_REGISTRIEREN
        try {
          System.out.println("__________Kunde Registrieren_________");
          System.out.println("\tName:");
          System.out.print("\t>");
          String name;

          name = GetInput(1, "\t>", null);

          System.out.println("\tEmail:");
          String email = GetInput(1, "\t>", null);
          System.out.println("\tAddresse:");
          String address = GetInput(1, "\t>", null);
          System.out.println("\tUser Name:");
          String un = GetInput(1, "\t>", null);
          System.out.println("\tPasswort:");
          String passwort = GetInput(1, "\t>", null);
          eshop.BV_kundeHinzufügen(name, un, passwort, email, address);

        } catch (ExceptionInputFalsch e) {
          System.out.println(e);
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
          string = GetInput();

          switch (string) {

            case "1":// Alle Artikel ausgeben
              System.out.println(eshop.AV_alleArtikel());
              break;
            case "2":// artikel suchen
              System.out.print("Suchen nach  > ");
              String searchTerm = GetInput();
              System.out.println(eshop.AV_sucheArtikel(searchTerm));
              break;
            case "3":// artikel ispizieren
              System.out.print("Artikel Name  > ");
              String titel = GetInput();
              try {
                System.out.println(eshop.AV_findArtikelByName(titel).toStringDetailled());
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e);
              }
              break;

            case "4":// artikel in den Warenkorb
              System.out.print("Artikel Name  > ");
              try {
                artikel = eshop.AV_findArtikelByName(GetInput());
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e);
              }
              System.out.print("Anzahl  > ");
              num = Integer.parseInt(GetInput());
              eshop.WV_setArtikel(artikel, num);
              break;

            case "5":// Warenkorb
              LevelMove(MenuLevel.WARENKORB);
              break;

            case "0":// Exit
              Logout();
              break;
          }
        } catch (ExceptionInputFalsch e) {
          System.out.println(e);
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
        System.out.print("\t>");

        try {
          string = GetInput();

          switch (string) {
            case "1":// ändern
              System.out.println("------Artikel anzahl ändern------");
              System.out.println("\tInput Artikel Name");
              System.out.print("\t>");
              try {
                artikel = eshop.AV_findArtikelByName(GetInput());
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e);
              }
              System.out.println("\tInput Artikel anzahl");
              System.out.print("\t>");
              num = Integer.parseInt(GetInput());

              eshop.WV_setArtikel(artikel, num);

              break;
            case "2":// löschen
              System.out.println("------Einzelnden Artikel löschen------");
              System.out.println("\tInput Artikel Name");
              System.out.print("\t>");
              try {
                artikel = eshop.AV_findArtikelByName(GetInput());
              } catch (ExceptionArtikelNichtGefunden e1) {
                // TODO runcatch
                System.out.println(e1);
              }

              eshop.WV_removeArtikel(artikel);
              break;
            case "3":// Alle löschen
              eshop.WV_clearAll();
              break;
            case "4":// KAUFEN
              System.out.println("------ KAUF BESTÄTIGUNG ------");
              System.out.println("\tKaufbestätigung:");
              System.out.println("\ty = ja kaufen");
              System.out.println("\tn = nein zurück");
              System.out.print("\t>");

              string = GetInput();

              if (string.equals("y")) {
                Rechnung rechnung;
                try {
                  rechnung = eshop.WV_kaufen(this.userHash);
                  System.out.print(rechnung);
                } catch (ExceptionArtikelCollection e) {
                  // TODO runcatch
                  System.out.println("Kaufen Fehl Geschlagen!");
                  System.out.println(e);
                }
              }

              break;
            case "0":// Exit
              LevelReturn();
              break;
          }
        } catch (ExceptionInputFalsch e) {
          System.out.println(e);
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
          string = GetInput();

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
          }
        } catch (ExceptionInputFalsch e) {
          System.out.println(e);
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
          string = GetInput();

          switch (string) {
            case "1":// detail ansicht artikel
              System.out.println("artikel name > ");
              try {
                artikelDetailAnsicht = eshop.AV_findArtikelByName(GetInput());
                LevelMove(MenuLevel.ARTIKELDETAILANSICHT);
              } catch (ExceptionArtikelNichtGefunden e1) {
                // TODO runcatch
                System.out.println(e1);
              }
              break;
            case "2":
              System.out.println("zu löschender Artikel > ");
              string = GetInput();
              try {
                eshop.AV_deleteArtikel(this.userHash, string);
              } catch (ExceptionArtikelNichtGefunden e1) {
                // TODO runcatch
                System.out.println(e1);
              }
              break;
            case "3":
              System.out.println("neuer Artikel name > ");
              String artikelName = GetInput();
              System.out.println("bestand > ");
              int bestand = Integer.parseInt(GetInput());
              System.out.println("Preis > ");
              double preis = Double.parseDouble(GetInput());

              Artikel art;
              try {
                eshop.AV_addArtikel(this.userHash, artikelName, bestand, preis);
                System.out.println("Erfolgreich erstellt!");
              } catch (ExceptionArtikelExistiertBereits e) {
                System.out.println("Etwas ist schief gelaufen.");
                // TODO runcatch
                System.out.println(e);
              }

              break;
            case "0":
              LevelReturn();
              break;
          }
        } catch (ExceptionInputFalsch e) {
          System.out.println(e);
        }
        break;
      // #endregion MITARBEITER_ARTIKEL
      case MITARBEITER_REGISTRIEREN:
        // #region MITARBEITER_REGISTRIEREN
        try {
          System.out.println("__________Mitarbeiter Registrieren_________");
          System.out.println("\tName:");
          System.out.print("\t>");
          String nam;

          nam = GetInput();

          System.out.println("\tusername:");
          System.out.print("\t>");
          String user = GetInput();
          System.out.println("\tpasswort:");
          System.out.print("\t>");
          String pass = GetInput();
          eshop.BV_mitarbeiterHinzufügen(nam, user, pass);
        } catch (ExceptionInputFalsch e) {
          System.out.println(e);
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
          string = GetInput();

          switch (string) {
            case "1"://
              // #region
              // #endregion
              break;
            case "2":// Detail ansicht
              // #region
              System.out.println("artikel name > ");
              try {
                artikelDetailAnsicht = eshop.AV_findArtikelByName(GetInput());
                LevelMove(MenuLevel.ARTIKELDETAILANSICHT);
              } catch (ExceptionArtikelNichtGefunden e) {
                // TODO runcatch
                System.out.println(e);
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
          }
        } catch (ExceptionInputFalsch e1) {
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
            string = GetInput();
            switch (string) {
              case "0":// Exit
                // #region
                LevelReturn();
                // #endregion
                break;
            }

          } catch (ExceptionInputFalsch e) {
            System.out.println(e);
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