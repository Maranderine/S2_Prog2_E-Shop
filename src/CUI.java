import java.util.Scanner;
import java.util.Stack;

import Domain.Eshop;

public class CUI {

  // create eshop
  private static Eshop eshop = new Eshop();
  // Create input Scanner
  static Scanner inputScanner = new Scanner(System.in);
  // loop main bool
  private static Boolean LOOP = true;

  /**
   * main methode
   * 
   * @param args()
   */
  public static void main(String args[]) {

    LevelMove(startLevel);

    String input;

    do {
      DisplayMenu();
      input = GetInput();
      ProcessInput(input);
    } while (LOOP);

    inputScanner.close();// closing the scanner
  }

  /**
   * gets an input as a string
   * 
   * @return
   */
  private static String GetInput() {
    return inputScanner.nextLine();
  }

  /**
   * processes input for the CUI
   * 
   * @param input
   */
  private static void ProcessInput(String input) {
    // first switch determines the menu level, the menu that should be displayed
    switch (Level()) {
      case MAIN_MENU:// main menu
        switch (input) {
          case "1":// Anmelden

            break;
          case "2":// Registrieren

            // eshop.KundeHinzufügen(name, username, password, nr, email, adress);1

            break;
          case "0":// Exit
            endCUI();
            break;
        }
        break;
      case LOGIN:// Login menu
        switch (input) {
          case "":

            break;
        }
        break;
      case WARENKORB:// Warenkorb menu
        switch (input) {
          case "1":// ändern
            // eshop.WK_setArtikel(artikel, integar);
            break;
          case "2":// löschen
            // eshop.WK_removeArtikel(artikel);
            break;
          case "3":// Alle löschen
            eshop.WK_clearAll();
            break;
          case "0":// Exit

            break;
        }
        break;
      case KUNDEN_ANSICHT:
        break;
      case MITARBEITER_ANSICHT:
        break;
      default:
        break;
    }
  }

  /** Displays the menu */
  private static void DisplayMenu() {

    switch (Level()) {
      case MAIN_MENU:
        System.out.println("____________E-Shop___________");
        System.out.println("1 = Anmelden");
        System.out.println("2 - Registrieren");
        System.out.println("0 = Exit");
        break;
      case LOGIN:
        System.out.println("LOGIN");
        break;
      case WARENKORB:
        System.out.println("____________Warenkorb____________");
        // System.out.println(warenkorb.getInhalt());//display warenkorb
        System.out.println("--------------------------------");
        System.out.println("1 = Artikel anzahl ändern");
        System.out.println("2 = Einzelnden Artikel löschen");
        System.out.println("3 = Alle Artikel löschen");
        System.out.println("0 = Exit");
        break;
      case KUNDEN_ANSICHT:
        break;
      case MITARBEITER_ANSICHT:
        break;
      default:
        break;
    }
  }

  // #region level system

  // menu level enum and value
  enum MenuLevel {
    MAIN_MENU, // start menu
    LOGIN,
    KUNDEN_ANSICHT,
    MITARBEITER_ANSICHT,
    WARENKORB
  }

  /** S */
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
      return MenuLevel.MAIN_MENU;

    return levelStack.pop();
  }

  private static MenuLevel Level() {
    return levelStack.peek();
  }

  // #endregion

  /**
   * Ends the CUI
   */
  private static void endCUI() {
    System.out.println("Ending CUI");
    LOOP = false;
  }

}