package UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import Domain.Eshop;
import Exceptions.Input.ExceptionInputExit;
import Exceptions.Input.ExceptionInputFalsch;
import Exceptions.Input.ExceptionInputFeldIstLeer;

/**
 * Parent for local usability classes like CUI or GUI
 * aall variables and methods must be implemented into child
 */
public abstract class UserInterface {
  // a unique hash value to identify the user when logged in
  public byte[] userHash;
  protected Eshop eshop;

  public UserInterface(Eshop eshop) {
    this.eshop = eshop;
  }

  /**
   * abstract standard run method.
   * is the main loop of the User Interface.
   * Must be inherited.
   */
  public abstract boolean run();

  /**
   * displays Exception message without stacktrace in
   * redefined format
   * 
   * @param e
   */
  protected void runCatch(Exception e) {
    System.out.print(e.getMessage() + "\n\n");
  }

  // #region input

  /** Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen */
  private BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));

  // #region character collections
  /** buchstaben klein a-z */
  private String cc_bk = "a-z";
  /** buchstaben groß A-Z */
  private String cc_bg = "A-Z";
  /** locals klein äöüß */
  // private String cc_lk = "äöüß"; Umlaute funktionieren nicht??? :(
  /** locals Groß ÄÖÜ */
  // private String cc_lg = "ÄÖÜ";

  /** buchstaben alle klein */
  private String cc_abk = /* cc_lk + */ cc_bk;
  /** buchstaben alle groß */
  private String cc_abg = /* cc_lg + */ cc_bg;
  /** buchstaben alle */
  private String cc_ab = cc_abk + cc_abg;

  /** Nummern */
  private String cc_n = "0-9";
  /** Wort Bindungs Zeichen */
  private String cc_wB = "-_";
  /** sonder zeichen */
  private String cc_sz = "!@#$%^&*";
  /** alles: Buchstaben, zahlen, bindezeichen */
  private String cc_all = cc_ab + cc_n + cc_wB;
  // #endregion

  // #region precompiled patterns
  /**
   * 1ne beliebig lange grupierungen an Buchstaben und zahlen mit umlauten,
   * zahlen, Bindezeichen und whitespaces
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternText = Pattern.compile("(?U)[" + cc_all + "\\s]+");
  // protected Pattern PatternText = Pattern.compile("(?U)[" + cc_all + "\\s]+");
  /**
   * 1ne beliebig lange grupierungen an Buchstaben mit umlauten, zahlen, und Binde
   * zeichen ohne whitespaces
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternUserName = Pattern.compile("(?U)[" + cc_all + "\\S]+");
  /**
   * mindestends 2 belieblig lange grupierungen an klein buchstaben, die mit einem
   * großbuchstaben anfangen, alle von einem wihte space getrennt
   * 
   * @see UserInterface.cc_abg
   * @see UserInterface.cc_abk
   */
  protected Pattern PatternEchtNamen = Pattern
      .compile("(?U)[" + cc_abg + "][" + cc_abk + "]*(\\s[" + cc_abg + "][" + cc_abk + "]*)+");
  /**
   * beliebig viele und lange grupierungen an allen zeichen die von 1 whitespace
   * getrennt werden
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternArtikel = Pattern.compile("(?U)[" + cc_all + "]+(\\s[" + cc_all + "]+)*");
  /**
   * 1ne positieve beliebig lange nummer ohne whitespaces
   * 
   * @see UserInterface.cc_n
   */
  protected Pattern PatternNum = Pattern.compile("(?U)[" + cc_n + "]+");
  /**
   * 1 oder kein minus, 1ne beliebig lange nummer
   * 
   * @see UserInterface.cc_n
   */
  protected Pattern PatternInt = Pattern.compile("(?U)(-)?[" + cc_n + "]+");
  /**
   * 1 oder kein minus, 1ne beliebig lange nummer, optional ein punkt und 1ne
   * beliebig lange nummer
   * 
   * @see UserInterface.cc_n
   * @see UserInterface.cc_all
   */
  protected Pattern PatternFloat = Pattern.compile("(?U)(-)?[" + cc_n + "]+((.)[" + cc_all + "]+)?");
  /**
   * eine email adresse
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternEmail = Pattern.compile("(?U)[" + cc_all + "]+(@)[" + cc_all + "]+(.)[" + cc_all + "]+");
  /**
   * ein passwort
   * 
   * @see UserInterface.cc_all
   * @see UserInterface.cc_sz
   */
  protected Pattern PatternPasswort = Pattern.compile("(?U)[" + cc_all + cc_sz + "\\s]+");
  /**
   * nach diesem format: StraßenName 11
   * 
   * @see UserInterface.cc_ab
   * @see UserInterface.cc_wB
   * @see UserInterface.cc_ab
   * @see UserInterface.cc_n
   */
  protected Pattern PatternAdresseStraße = Pattern
      .compile("(?U)[" + cc_ab + "]+(\\s|" + cc_wB + "[" + cc_ab + "]+)*\\s[" + cc_n + "]+");
  /**
   * nach diesem format: 99999 Ortsname
   * 
   * @see UserInterface.cc_n
   * @see UserInterface.cc_abg
   * @see UserInterface.cc_abk
   * @see UserInterface.cc_wB
   * @see UserInterface.cc_ab
   */
  protected Pattern PatternAdresseOrt = Pattern
      .compile("(?U)[" + cc_n + "]{5}\\s[" + cc_abg + "][" + cc_abk + "]*(\\s|" + cc_wB + "[" + cc_ab + "]+)*");
  /**
   * nach diesem format: Deutschland
   * 
   * @see UserInterface.cc_abg
   * @see UserInterface.cc_abk
   */
  protected Pattern PatternAdresseLand = Pattern
      .compile("(?U)[" + cc_abg + "][" + cc_abk + "]+");

  // #endregion

  // #region input methoden

  /**
   * gets an input as a string
   * 
   * @param loopnum         number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @param pattern         regex pattern oder null
   * @param wrongInputText  text der erklärt welche inputs erlaubt sind
   * @param linePre         before every printed line of text
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInput(int loopNummer, String inputEinleitung, String exitPhrase, Pattern pattern,
      String wrongInputText, String linePre) throws ExceptionInputFalsch, ExceptionInputExit {
    String input;
    if (linePre == null)
      linePre = "";
    if (exitPhrase != null)
      System.out.println(linePre + "Zurück input: " + exitPhrase);

    do {
      // display intro
      if (inputEinleitung != null)
        System.out.print(linePre + inputEinleitung);
      // get input
      try {
        input = inputStream.readLine();
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }

      // input auswertung

      // esit?
      if (exitPhrase != null)
        if (input.equals(exitPhrase)) {
          throw new ExceptionInputExit();
        }
      // check input für fehler
      try {
        if (input.isEmpty()) {
          throw new ExceptionInputFeldIstLeer();
        } else {
          if (pattern != null) {
            if (pattern.matcher(input).matches())
              return input;
            else
              throw new ExceptionInputFalsch(wrongInputText);
          } else
            return input;// no pattern return input
        }
      } catch (Exception e) {
        System.out.println(linePre + e.getMessage());
      }

    } while (--loopNummer != 0);

    throw new ExceptionInputFalsch(wrongInputText);
  }

  /**
   * einfach nur input nehmen, ohne pattern matching und input erklärung
   * 
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInput(int loopNummer, String inputEinleitung, String exitPhrase, Pattern pattern,
      String wrongInputText)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, pattern, wrongInputText, "");
  }

  /**
   * einfach nur input nehmen, ohne pattern matching und input erklärung
   * 
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInput(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, null, "");
  }

  /**
   * einfach nur input nehmen
   * 
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInput() throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(1, null, null, null, "");
  }

  // premade pattern matching

  /**
   * erlaubt nur text eingabe ohne sonderzeichen
   * 
   * @see UserInterface.PatternText
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputText(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternText,
        "Keine zeichen ausser: -_");
  }

  /**
   * erlaubt nur Echt Namen input.
   * mindestends 2 belieblig lange grupierungen an klein buchstaben, die mit einem
   * großbuchstaben anfangen, alle von einem wihte space getrennt
   * 
   * @see UserInterface.PatternEchtNamen
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputNamen(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternEchtNamen,
        "Darf keine Zeichen, Zahlen oder extra Leerzeichen enthalten.\nz.B: Hanna Maria Antonia Rodrigez");
  }

  /**
   * erlaubt nur Artikel namen konventionen input.
   * beliebig viele und lange grupierungen an allen zeichen die von 1 whitespace
   * getrennt werden
   * 
   * @see UserInterface.PatternArtikel
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputArtikel(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternArtikel,
        "Darf keine Sonderzeichen oder extra Leerzeichen enthalten.");
  }

  /**
   * erlaubt nur simplen zahlen input, eine beliebig lange positive Zahl ohne
   * Nachkommastelle
   * 
   * @see UserInterface.PatternNum
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputNum(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternNum,
        "Eine beliebig lange positive Zahl ohne Nachkommastelle.\nz.B. 100, 4, 99999999");
  }

  /**
   * erlaubt nur navigations input, eine beliebig lange positive Zahl ohne
   * Nachkommastelle
   * 
   * @see UserInterface.PatternNum
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   */
  protected String GetInputNavigation(String inputEinleitung)
      throws ExceptionInputFalsch {
    try {
      return GetInput(0, inputEinleitung, null, PatternNum,
          "Eine der in der Navigation angegebenen Nummern.");
    } catch (ExceptionInputExit e) {
      return "";
    }
  }

  /**
   * erlaubt nur int eingaben
   * 
   * @see UserInterface.PatternInt
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputInt(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternInt,
        "Eine beliebig lange Zahl ohne Nachkommastelle.\nz.B: 1, 99, -7");
  }

  /**
   * erlaubt nur float eingaben
   * 
   * @see UserInterface.PatternFloat
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputFloat(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternFloat,
        "Eine beliebig lange Zahl.\nz.B: 9, 20, 11.5, -99.003");
  }

  /**
   * erlaubt nur email eingaben
   * 
   * @see UserInterface.PatternEmail
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputEmail(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternEmail,
        "Eine Email: 'xxxx@xxxx.xxx'\nz.B: max.mustermann99@test.de");
  }

  /**
   * erlaubt adress eingaben nach diesem format: StraßenName 11, 99999 Ortsname,
   * Deutschland.
   * Input nahme ist in 3 schritte aufgeteilt: Staße, Ort, Land
   * 
   * @see UserInterface.
   * @param loopNummer number of loops to try, 1 to run one time, X<=0 for
   *                   infinite loops
   * @param exitPhrase eingabe die den loop verlässt oder null für nichts,
   *                   wird vorher einmal ausgegeben: "Zurück input: " +
   *                   exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputAdresse(int loopNummer, String erstEinleitung, String exitPhrase, String linePre)
      throws ExceptionInputFalsch, ExceptionInputExit {
    String str = "";
    System.out.println(erstEinleitung);
    str += GetInput(loopNummer, "Straße - Format: \"StraßenName 11\"\n\t > ", exitPhrase,
        PatternAdresseStraße, "Nur eingaben mit dem gewollten format werden erkannt.", linePre);
    str += GetInput(loopNummer, "Ort - Format: \"99999 Ortsname\"\n\t > ", exitPhrase, PatternAdresseOrt,
        "Nur eingaben mit dem gewollten format werden erkannt.", linePre);
    str += GetInput(loopNummer, "Land - Format: \"Deutschland\"\"\n\t > ", exitPhrase, PatternAdresseLand,
        "Nur eingaben mit dem gewollten format werden erkannt.", linePre);

    return str;
  }

  /**
   * erlaubt nur passwort input, gibt ausgabe zu erlaubten zeichen.
   * Bei passwort abfrage bitte GetInputPasswortAbfrage benutzen.
   * 
   * @see UserInterface.PatternPasswort
   * @see UserInterface.GetInputPasswortAbfrage
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputPasswortNew(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternPasswort,
        "Darf keine sonderzeichen enthalten ausser: " + cc_sz);
  }

  /**
   * erlaubt nur passwort input, gibt KEINE ausgabe zu erlaubten zeichen.
   * Bei passwort neu erstellung bitte GetInputPasswortNew benutzen.
   * 
   * @see UserInterface.PatternPasswort
   * @see UserInterface.GetInputPasswortNew
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   */
  protected String GetInputPasswortAbfrage(String inputEinleitung) {
    try {
      return GetInput(1, inputEinleitung, null, PatternPasswort,
          null);
    } catch (ExceptionInputFalsch | ExceptionInputExit e) {
      // falscher typ input muss nicht angezeigt werden
      return "";
    }
  }

  /**
   * erlaubt usernamen eingabe. gibt ausgabe zu erlaubten zeichen.
   * Bei usernamen abfrage bitte GetInputUserNameAbfrage benutzen.
   * 1ne beliebig lange grupierungen an Buchstaben mit umlauten, zahlen, und Binde
   * zeichen ohne whitespaces
   * 
   * @see UserInterface.PatternUserName
   * @see UserInterface.GetInputUserNameAbfrage
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws ExceptionInputExit
   */
  protected String GetInputUserNameNew(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch, ExceptionInputExit {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternUserName,
        "Darf Buchstaben, Nummern und Zeichen enthalten ausser: " + cc_sz + "\nUnd keine Leerzeichen enthalten.");
  }

  /**
   * erlaubt usernamen eingabe. gibt KEINE ausgabe zu erlaubten zeichen.
   * Bei usernamen neu erstellung bitte GetInputUserNameNew benutzen.
   * 1ne beliebig lange grupierungen an Buchstaben mit umlauten, zahlen, und Binde
   * zeichen ohne whitespaces
   * 
   * @see UserInterface.PatternUserName
   * @see UserInterface.GetInputUserNameNew
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts,
   *                        wird vorher einmal ausgegeben: "Zurück input: " +
   *                        exitPhrase
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   */
  protected String GetInputUserNameAbfrage(String inputEinleitung) {
    try {
      return GetInput(1, inputEinleitung, null, PatternUserName,
          null);
    } catch (ExceptionInputFalsch | ExceptionInputExit e) {
      // falscher typ input muss nicht angezeigt werden
      return "";
    }
  }

  // #endregion

  protected void regexChecker(Pattern pat) {
    String input;

    do {
      try {
        input = inputStream.readLine();
        System.out.println("Pattern: " + pat.matcher(input).matches());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } while (true);
  }

  // #endregion
}