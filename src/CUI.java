import BenutzerObjekte.Benutzer;
import DatenObjekte.*;
import BenutzerObjekte.Kunde;
import BenutzerObjekte.Mitarbeiter;
import Exceptions.*;

import java.util.Scanner;

public class CUI {
  // #region SETUP
  // menu level enum and value
  enum MenuLevel {
    MAIN, // start menu
    LOGIN,
    KUNDEN_ANSICHSRT,
    MITARBEITER_ANSICHT,
    WARENKORB
  }

  private static MenuLevel menuLevel;
  // Create input Scanner
  static Scanner inputScanner = new Scanner(System.in);
  // loop main bool
  private static Boolean LOOP = true;

  /**
   * Default constructor
   * Menu Starts in default state: Main Menu
   */
  private CUI() {
    menuLevel = MenuLevel.MAIN;
  }

  /**
   * Custom constructor
   * Menu Starts in set state
   * 
   * @param level
   */
  private CUI(MenuLevel level) {
    menuLevel = level;
  }

  // #endregion SETUP
  /**
   * main methode
   * 
   * @param args()
   */
  public static void main(String args[]) {
    // main program loop
    //keeps looping until endCUI() is called
    do{
      DisplayMenu();
      String input = getInput();
      processInput(input);
    }while(LOOP)

    inputScanner.close();// closing the scanner
  }

  private static String getInput() {
    return inputScanner.nextLine();
  }

  /**
   * processes input for the CUI
   * @param input
   */
  private static void processInput(String input) {
    //first switch determines the menu level, the menu that should be displayed
    switch (menuLevel) {
      case MAIN://main menu
        switch (input) {
          case "1":// Anmelden

            break;
          case "2":// Registrieren

            break;
          case "0":// Exit
            endCUI();
            break;
        }
        break;
      case LOGIN://Login menu
        switch (input) {
          case "":
            
            break;
        }
        break;
      case WARENKORB://Warenkorb menu
        switch (input) {
          case "1":// ändern
            warenkorb.setArtikel(artikel, integar);
            break;
          case "2":// löschen
            warenkorb.
            break;
          case "3":// Alle löschen

            break;
          case "0":// Exit

            break;
        }
        break;
    }

  private static void DisplayMenu() {
    switch (menuLevel) {
      case MAIN:
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
