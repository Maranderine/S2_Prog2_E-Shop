package UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

import Domain.Eshop;
import Domain.Artikel.Artikel;
import Domain.Warenkorb.Rechnung;
import Exceptions.Artikel.ExceptionArtikelNichtGefunden;
import Exceptions.Input.ExceptionInputFeldIstLeer;

public class CUI extends UserInterface {

  public CUI(Eshop eshop) {
    super(eshop);
    // move level to start menu
    LevelMove(startLevel);
  }

  /** Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen */
  private BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));

  // #region input
  /**
   * gets an input as a string
   * 
   * @param loopnum         number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null
   * @param exitPhrase      to exit the input loop oder null
   * @return input
   * @throws IOException
   */
  private String GetInput(int loopNummer, String inputEinleitung, String exitPhrase) {

    String input;

    do {
      // display intro
      if (inputEinleitung != null)
        System.out.print(inputEinleitung);
      // get input
      try {
        input = this.inputStream.readLine();
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }

      if (!exitPhrase.equals(null))
        if (input.equals(exitPhrase)) {
          return null;
        }

      try {
        if (input.isBlank())
          throw new ExceptionInputFeldIstLeer();
        else {
          return input;
        }

        String.format

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

    } while (--loopNummer != 0);

    return null;
  }
  // #endregion

  /** Display and process of the CUI menu */
  public boolean run() {

    int num;
    String string;
    String string2;
    String string3;
    Artikel artikel = null;
    Artikel artikelDetailAnsicht;

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

        string = GetInput(1, null, null);

        switch (string) {
          case "1":// Anmelden
            // LevelMove(MenuLevel.ANMELDUNG);
            // #region Anmelden
            System.out.println("LOGIN");
            System.out.print("username > ");
            String username = GetInput(1, null, null);

            System.out.print("password  > ");
            String password = GetInput(1, null, null);

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
        break;
      // #endregion MAIN_MENU
      case KUNDEN_REGISTRIEREN:
        // #region KUNDEN_REGISTRIEREN
        System.out.println("__________Kunde Registrieren_________");
        System.out.println("\tName:");
        System.out.print("\t>");
        String name = GetInput();
        System.out.println("\temail:");
        System.out.print("\t>");
        String email = GetInput();
        System.out.println("\taddresse:");
        System.out.print("\t>");
        String address = GetInput();
        System.out.println("\tusername:");
        System.out.print("\t>");
        String un = GetInput();
        System.out.println("\tpasswort:");
        System.out.print("\t>");
        String passwort = GetInput();
        eshop.BV_kundeHinzufügen(name, un, passwort, email, address);
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

        string = GetInput();
        switch (string) {
          case "1":// ändern
            System.out.println("------Artikel anzahl ändern------");
            System.out.println("\tInput Artikel Name");
            System.out.print("\t>");
            artikel = eshop.AV_findArtikelByName(GetInput());
            System.out.println("\tInput Artikel anzahl");
            System.out.print("\t>");
            num = Integer.parseInt(GetInput());

            eshop.WV_setArtikel(artikel, num);

            break;
          case "2":// löschen
            System.out.println("------Einzelnden Artikel löschen------");
            System.out.println("\tInput Artikel Name");
            System.out.print("\t>");
            artikel = eshop.AV_findArtikelByName(GetInput());

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
              Rechnung rechnung = eshop.WV_kaufen(this.userHash);

              // Artikel falsch text
              if (rechnung == null) {
                System.out.println("Kaufen Fehl Geschlagen!");
              } else {
                System.out.print(rechnung);
              }
            }

            break;
          case "0":// Exit
            LevelReturn();
            break;
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

        string = GetInput();

        switch (string) {
          case "1":
            System.out.println("artikel name > ");
            artikelDetailAnsicht = eshop.AV_findArtikelByName(GetInput());
            if (artikelDetailAnsicht != null) {
              LevelMove(MenuLevel.ARTIKELDETAILANSICHT);
            }
            break;
          case "2":
            System.out.println("zu löschender Artikel > ");
            string = GetInput();
            eshop.AV_deleteArtikel(this.userHash, string);
            break;
          case "3":
            System.out.println("neuer Artikel name > ");
            String artikelName = GetInput();
            System.out.println("bestand > ");
            int bestand = Integer.parseInt(GetInput());
            System.out.println("Preis > ");
            double preis = Double.parseDouble(GetInput());
            // TODO CUI Artikel: was wenn falsche eingabe
            Artikel art = eshop.AV_addArtikel(this.userHash, artikelName, bestand, preis);
            if (art != null)
              System.out.println("Erfolgreich erstellt!");
            else
              System.out.println("Etwas ist schief gelaufen.");
            break;
          case "0":
            LevelReturn();
            break;
        }
        break;
      // #endregion MITARBEITER_ARTIKEL
      case MITARBEITER_REGISTRIEREN:
        // #region MITARBEITER_REGISTRIEREN
        System.out.println("__________Mitarbeiter Registrieren_________");
        System.out.println("\tName:");
        System.out.print("\t>");
        String nam = GetInput();
        System.out.println("\tusername:");
        System.out.print("\t>");
        String user = GetInput();
        System.out.println("\tpasswort:");
        System.out.print("\t>");
        String pass = GetInput();
        eshop.BV_mitarbeiterHinzufügen(nam, user, pass);

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

        string = GetInput();

        switch (string) {
          case "1"://
            // #region
            // #endregion
            break;
          case "2":// Detail ansicht
            // #region
            System.out.println("artikel name > ");
            artikelDetailAnsicht = eshop.AV_findArtikelByName(GetInput());

            System.out.println("\nArtikel konnte nicht gefunden werden\n");
            LevelReturn();
            LevelMove(MenuLevel.ARTIKELDETAILANSICHT);

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

        break;
      // #endregion MITARBEITER_EREIGNISLOG
      case ARTIKELDETAILANSICHT:
        // #region

        System.out.println("____________Artikel Detaille ansicht____________");
        System.out.print(artikelDetailAnsicht.toStringDetailled());
        System.out.println("------------------------------------");
        System.out.println("0 = Exit");

        string = GetInput();

        switch (string) {
          case "0":// Exit
            // #region
            LevelReturn();
            // #endregion
            break;
        }

        // #endregion
        break;
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