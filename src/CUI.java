import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

import BenutzerObjekte.Benutzerverwaltung;
import DatenObjekte.Artikel;
import Domain.Eshop;

public class CUI {

  // create eshop
  private static Eshop eshop = new Eshop();
  /** Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen */
  private static BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
  // loop main bool
  private static Boolean LOOP = true;

  /**
   * main methode
   * 
   * @param args()
   */
  public static void main(String args[]) {

    // #region setup

    // move level to start menu
    LevelMove(startLevel);

    // #endregion setup

    // #region TEMP PLEASE DELETE FOR FINAL PRODUCT
    LevelMove(MenuLevel.WARENKORB);
    eshop.WV_setArtikel(eshop.AV_addArtikel("TEST1", 1, 1.99), 1);
    eshop.WV_setArtikel(eshop.AV_addArtikel("TEST2", 1, 1.55), 1);
    eshop.WV_setArtikel(eshop.AV_addArtikel("TEST3", 1, 1.66), 1);
    eshop.WV_setArtikel(eshop.AV_addArtikel("TEST4", 1, 1.77), 1);

    // #endregion TEMP PLEASE DELETE FOR FINAL PRODUCT

    do {
      CUImenu();
    } while (LOOP);
  }

  // #region input
  /**
   * gets an input as a string
   * 
   * @return input
   * @throws IOException
   */
  private static String GetInput() {
    try {
      return inputStream.readLine();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  // #endregion

  /** Display and process of the CUI menu */
  private static void CUImenu() {

    int num;
    String string;
    Artikel artikel;

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

        string = GetInput();

        switch (string) {
          case "1":// Anmelden
            LevelMove(MenuLevel.ANMELDUNG);
            break;
          case "2":// Registrieren
            LevelMove(MenuLevel.KUNDEN_REGISTRIEREN);
            break;
          case "0":// Exit
            endCUI();
            break;
        }
        break;
        //#endregion MAIN_MENU
      case ANMELDUNG:// Login menu
        //#region ANMELDUNG

        System.out.println("LOGIN");
        System.out.println("username > ");
				String username = GetInput();
				System.out.print("password  > ");
				String password = GetInput();

        // Benutzerverwaltung.AktiverNutzer nutzer = eshop.login(username, password);
        switch(eshop.login(username, password)){
          case NONE:
            System.out.println(" Benutzername oder Passwort falsch");
            break;
          case MITARBEITER:
            LevelMove(MenuLevel.MITARBEITER_ANSICHT);
            break;

          case KUNDE:
           LevelMove(MenuLevel.MITARBEITER_ANSICHT);
           break;
        }
        break;
      // #endregion ANMELDUNG
      case KUNDEN_REGISTRIEREN:
      // #region KUNDEN_REGISTRIEREN




        
        break;
        // #endregion KUNDEN_REGISTRIEREN
      case KUNDEN_ANSICHT:
        // #region KUNDEN_ANSICHT
        System.out.println("____________KUNDE____________");
        System.out.println("1 = alle Artikel ausgeben");
        System.out.println("2 = Artikel suchen");
        System.out.println("3 = Artikel dem Warenkorb hinzufügen");
        System.out.println("4 = Warenkorb anzeigen");
        System.out.println("0 = Exit");

        string = GetInput();
        
        switch (string) {
 
          case "1":// Artikel ausgeben
            new Vector<Artikel> artikelListe = eshop.alleArtikel();
            gibArtikelListeAus(artikelListe);
            break;
          case "2":// artikel suchen
            System.out.print("Artikel Name  > ");
				    string = GetInput();
            Vector<Artikel> artikelListe = new Vector<Artikel>;
            artikelListe = eshop.searchArtikel(string);
            gibArtikelListeAus(artikelListe);
            break;

          case "3":// artikel in den Warenkorb 
            System.out.print("Artikel Name  > ");
				    string = GetInput();
            System.out.print("Anzahl  > ");
				    String anzahl = GetInput();
            int anz = Integer.parseInt(anzahl);
            WarenkorbVw.setArtikel(string, anz);
            break;
            
          case "4":// Warenkorb
            LevelMove(MenuLevel.WARENKORB);
            break;

          case "0":// Exit
            LevelReturn();

            break;
        }
        break;
      // #endregion KUNDEN_ANSICHT
      case KUNDEN_ARTIKEL:
        // #region KUNDEN_ARTIKEL
        break;
      // #endregion KUNDEN_ARTIKEL
      case WARENKORB:
        // #region WARENKORB
        System.out.println("____________________Warenkorb_____________________");
        System.out.println(eshop.WK_getWarenkorb());// display warenkorb
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

            


            break;
          case "0":// Exit
            LevelReturn();
            break;
        }
        break;
      // #endregion WARENKORB
      case MITARBEITER_ANSICHT:
        // #region MITARBEITER_ANSICHT
        System.out.println("____________MITARBEITER____________");
        System.out.println("1 = Artikel hinzufügen");
        System.out.println("2 = Artikel Bestand ändern");
        System.out.println("3 = Mitarbeiter hinzufügen");
        System.out.println("0 = Exit");

        string = GetInput();

        switch(string){
        case "1"://artikel hinzufügen

          break;
        case "2"://Bestand ändern

          break;
        case "3"://Mitarbeiter hinzufügen
          
          break;
        case "0":
          LevelReturn();
          break;
      }
      break;
      // #endregion MITARBEITER_ANSICHT
      case MITARBEITER_ARTIKEL:
        // #region MITARBEITER_ARTIKEL
        break;
      // #endregion MITARBEITER_ARTIKEL
      case MITARBEITER_REGISTRIEREN:
        // #region MITARBEITER_REGISTRIEREN
        System.out.println("__________Mitarbeiter Registrieren_________");
        
        System.out.println("\tName:");
        System.out.print("\t>");
        String name = GetInput();
        System.out.println("\tusername:");
        System.out.print("\t>");
        String un = GetInput();
        System.out.println("\tpasswort:");
        System.out.print("\t>");
        String passwort = GetInput();
        System.out.println("\tnummer:");
        System.out.print("\t>");
        int nummer = Integer.parseInt(GetInput());

        eshop.mitarbeiterHinzufügen(name, un, passwort, nummer);
        break;
      // #endregion MITARBEITER_REGISTRIEREN
      case MITARBEITER_EREIGNISLOG:
        // #region MITARBEITER_EREIGNISLOG





        break;
        // #endregion MITARBEITER_EREIGNISLOG
      
    }
  }

  // #region level system ///////////////////////

  // menu level enum and value
  enum MenuLevel {
    MAIN_MENU, // start menu
    ANMELDUNG,
    KUNDEN_REGISTRIEREN,
    KUNDEN_ANSICHT,
    KUNDEN_ARTIKEL,
    WARENKORB,
    MITARBEITER_ANSICHT,
    MITARBEITER_ARTIKEL,
    MITARBEITER_REGISTRIEREN,
    MITARBEITER_EREIGNISLOG
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
  private static MenuLevel LevelClear() {
    levelStack.clear();
    levelStack.push(startLevel);
    return startLevel;
  }

  // #endregion ///////////////////////

  /**
   * Ends the CUI
   */
  private static void endCUI() {
    System.out.println("Ending CUI");
    LOOP = false;
  }

}