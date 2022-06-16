package UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import Domain.Eshop;
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

  // #region input

  /** Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen */
  private BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));

  // #region character collections
  /** buchstaben klein a-z */
  private String cc_bk = "a-z";
  /** buchstaben groß A-Z */
  private String cc_bg = "A-Z";
  /** Umlaute klein ä-ü */
  private String cc_uk = "ä-ü";
  /** Umlaute Groß Ä-Ü */
  private String cc_ug = "Ä-Ü";

  /** buchstaben alle klein */
  private String cc_abk = cc_uk + cc_bk;
  /** buchstaben alle groß */
  private String cc_abg = cc_ug + cc_bg;
  /** buchstaben alle */
  private String cc_ab = cc_abk + cc_abg;

  /** Nummern */
  private String cc_n = "0-9";
  /** Wort Bindungs Zeichen */
  private String cc_wB = "-_";
  /** alles: Buchstaben, zahlen, bindezeichen */
  private String cc_all = cc_ab + cc_n + cc_wB;
  // #endregion

  // #region precompiled patterns
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

  // #endregion

  // #region input methoden

  /**
   * gets an input as a string
   * 
   * @param loopnum         number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @param pattern         regex pattern oder null
   * @param wrongInputText  text der erklärt welche inputs erlaubt sind
   * @return String eingegebener input
   * @throws ExceptionInputFalsch
   * @throws IOException
   */
  protected String GetInput(int loopNummer, String inputEinleitung, String exitPhrase, Pattern pattern,
      String wrongInputText) throws ExceptionInputFalsch {
    String input;

    do {
      // display intro
      if (inputEinleitung != null)
        System.out.print(inputEinleitung);
      // get input
      try {
        input = inputStream.readLine();
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }

      if (exitPhrase != null)
        if (input.equals(exitPhrase)) {
          return null;
        }

      // input checks
      try {
        if (input.isBlank()) {
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
        System.out.println(e.getMessage());
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
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInput(int loopNummer, String inputEinleitung, String exitPhrase) throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, null, "");
  }

  /**
   * einfach nur input nehmen
   * 
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInput() throws ExceptionInputFalsch {
    return GetInput(1, null, null, null, "");
  }

  // premade pattern matching
  /**
   * erlaubt usernamen eingabe.
   * 1ne beliebig lange grupierungen an Buchstaben mit umlauten, zahlen, und Binde
   * zeichen ohne whitespaces
   * 
   * @see UserInterface.PatternUserName
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputUserName(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternUserName,
        "einene Benutzer Namen. Darf keine Leerzeichen enthalten.");
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
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputNamen(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternEchtNamen,
        "einen Namen. Darf keine Zeichen, Zahlen oder extra Leerzeichen enthalten.");
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
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputArtikel(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternArtikel,
        "einen Artikel namen. Darf keine Sonderzeichen oder extra Leerzeichen enthalten.");
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
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputNum(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternNum,
        "eine beliebig lange positive Zahl ohne Nachkommastelle.");
  }

  /**
   * erlaubt nur navigations input, eine beliebig lange positive Zahl ohne
   * Nachkommastelle
   * 
   * @see UserInterface.PatternNum
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputNavigation(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternNum,
        "eine der in der Navigation angegebenen Nummern.");
  }

  /**
   * erlaubt nur int eingaben
   * 
   * @see UserInterface.PatternInt
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputInt(int loopNummer, String inputEinleitung, String exitPhrase) throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternInt,
        "eine beliebig lange Zahl ohne Nachkommastelle.");
  }

  /**
   * erlaubt nur float eingaben
   * 
   * @see UserInterface.PatternFloat
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputFloat(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternFloat,
        "eine beliebig lange Zahl.");
  }

  /**
   * erlaubt nur email eingaben
   * 
   * @see UserInterface.PatternEmail
   * @param loopNummer      number of loops to try, 1 to run one time, X<=0 for
   *                        infinite loops
   * @param inputEinleitung string gezeigt vor input nahme oder null für nichts
   * @param exitPhrase      eingabe die den loop verlässt oder null für nichts
   * @return
   * @throws ExceptionInputFalsch
   */
  protected String GetInputEmail(int loopNummer, String inputEinleitung, String exitPhrase)
      throws ExceptionInputFalsch {
    return GetInput(loopNummer, inputEinleitung, exitPhrase, PatternEmail,
        "eine Email: 'xxxx@xxxx.xxx'");
  }

  // #endregion

  protected void regexChecker() {

    String input;

    do {

      try {
        input = inputStream.readLine();
        System.out.println("Pattern: " + PatternEchtNamen.matcher(input).matches());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } while (true);
  }

  // #endregion
}