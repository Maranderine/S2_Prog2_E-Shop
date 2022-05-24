import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.Vector;

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
    String string2;
    Artikel artikel;
    String getInput;

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

        getInput = GetInput();

        switch (getInput) {
          case "1":// Anmelden
            LevelMove(MenuLevel.LOGIN);
            break;
          case "2":// Registrieren
            LevelMove(MenuLevel.KUNDEN_REGISTRIEREN);
            break;
          case "0":// Exit
            endCUI();
            break;
        }
        break;
      case LOGIN:// Login menu
        System.out.println("LOGIN");
        System.out.println("username > ");
				string = GetInput();
				System.out.print("password  > ");
				string2 = GetInput();

        switch(eshop.login(string, string2)){
          case 0:
            System.out.println(" Benutzername oder Passwort falsch");
            break;
          case 1:
            LevelMove(MenuLevel.MITARBEITER_ANSICHT);
            break;

          case 2:
           LevelMove(MenuLevel.KUNDEN_ANSICHT);
           break;
        }
        break;

      case WARENKORB:
        // #region WARENKORB
        System.out.println("____________________Warenkorb_____________________");
        System.out.println(eshop.WK_getWarenkorb());// display warenkorb
        System.out.println("--------------------------------------------------");
        System.out.println("\t1 = Artikel anzahl ändern");
        System.out.println("\t2 = Einzelnden Artikel löschen");
        System.out.println("\t3 = Alle Artikel löschen");
        System.out.println("\t0 = Exit");
        System.out.print("\t>");

        getInput = GetInput();

        switch(getInput) {
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
          case "0":// Exit
            LevelReturn();
            break;
        }
        break;
      case KUNDEN_ANSICHT:
        System.out.println("____________KUNDE____________");
        System.out.println("1 = alle Artikel ausgeben");
        System.out.println("2 = Artikel suchen");
        System.out.println("3 = Artikel dem Warenkorb hinzufügen");
        System.out.println("4 = Warenkorb");
        System.out.println("0 = Exit");

        getInput = GetInput();

        switch (getInput) {
 
          case "1":// Artikel ausgeben
            gibArtikelListeAus(eshop.AV_alleArtikel());
            break;

          case "2":// artikel suchen
            System.out.print("Artikel Name  > ");
				    String titel = GetInput();
            gibArtikelListeAus(eshop.AV_searchArtikel(titel));
            break;

          case "3":// artikel in den Warenkorb 
            System.out.print("Artikel Name  > ");
				    artikel = eshop.AV_findArtikelByName(GetInput());
            System.out.print("Anzahl  > ");
            num = Integer.parseInt(GetInput());
            eshop.WV_setArtikel(artikel, num);
            break;
            
          case "4":// Warenkorb
            LevelMove(MenuLevel.WARENKORB);
            break;

          case "0":// Exit
            LevelReturn();

            break;
        }
        break;

      case KUNDEN_ARTIKEL:
        // #region KUNDEN_ARTIKEL
        break;
      // #endregion KUNDEN_ARTIKEL
      case KUNDEN_REGISTRIEREN:
        // #region MITARBEITER_REGISTRIEREN
        break;

      case MITARBEITER_ANSICHT:
        System.out.println("____________MITARBEITER____________");
        System.out.println("1 = Artikel Verwalten");
        System.out.println("2 = Mitarbeiter hinzufügen");
        System.out.println("0 = Exit");

        getInput = GetInput();
          
        switch(getInput){
          case "1"://artikel Verwalten
          LevelMove(MenuLevel.MITARBEITER_ARTIKEL);
            break;
          case "2"://Mitarbeiter hinzufügen
          LevelMove(MenuLevel.MITARBEITER_REGISTRIEREN);
            break;
          case "0":
            LevelReturn();
            break;
        }
        break;
      // #endregion MITARBEITER_ANSICHT
      case MITARBEITER_ARTIKEL:
      System.out.println("Artikel Verwalten");
        System.out.println("1 = Artikel löschen");
        System.out.println("2 = Atikel hinzufügen");
        System.out.println("3 = Artikel Bestand ändern");
        System.out.println("0 = Exit");

        getInput = GetInput();

        switch(getInput){

          case "1":
            break;
          case "2":
            break;
          case "3":
            break;
          case "0":
            LevelReturn();
            break;
        }

        break;
      // #endregion MITARBEITER_ARTIKEL
      case MITARBEITER_REGISTRIEREN:
        System.out.println("Name > ");
        getInput = GetInput();
        System.out.println("username > ");
        string = GetInput();
        System.out.println("passwort > ");
        string2 = GetInput();
        eshop.mitarbeiterHinzufügen(getInput, string, string2);//name, username, password
        // #region MITARBEITER_REGISTRIEREN
        break;
    }
  }

  // #region level system ///////////////////////

  // menu level enum and value
  enum MenuLevel {
    MAIN_MENU, // start menu
    LOGIN,
    KUNDEN_REGISTRIEREN,
    KUNDEN_ANSICHT,
    KUNDEN_ARTIKEL,
    WARENKORB,
    MITARBEITER_ANSICHT,
    MITARBEITER_ARTIKEL,
    MITARBEITER_REGISTRIEREN
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
  
   private static void gibArtikelListeAus(Vector<Artikel> artikelListe) {
		if (artikelListe.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Artikel artikel: artikelListe) {
				System.out.println(artikel);
			}
		}
	}

  /**
   * Ends the CUI
   */
  private static void endCUI() {
    System.out.println("Ending CUI");
    LOOP = false;
  }

}