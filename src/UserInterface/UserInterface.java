package UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

import Domain.Eshop;
import Exceptions.Input.ExceptionInputFalsch;

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
  private String cc_wB = ".-_";
  /** sonder zeichen */
  private String cc_sz = "!@#$%^&*";
  /** alles: Buchstaben, zahlen, bindezeichen */
  private String cc_all = cc_ab + cc_n + cc_wB;
  // #endregion
  // #region precompiled patterns

  /**
   * hält den erklärungs text zu jedem pattern
   * die texte sind an den user gerichtet
   */
  private HashMap<Pattern, String> patternInfoText = new HashMap<Pattern, String>();

  /**
   * gets the pattern associated user info string
   * 
   * @param pattern precompiled regex pattern
   * @return string
   */
  private String getPatternInfo(Pattern pattern) {
    return patternInfoText.get(pattern);
  }

  /**
   * 
   */
  /**
   * compiles a pattern for the given regex and add its user information text to
   * to the patternInfoText map
   * 
   * @param regex        regex pattern
   * @param userInfoText user informationen über gewollten input
   * @return Pattern pattern
   */
  private Pattern compilePattern(String regex, String userInfoText) {

    Pattern pattern = Pattern.compile(regex);
    patternInfoText.put(pattern, userInfoText);
    return pattern;
  }

  /**
   * 1ne beliebig lange grupierungen an Buchstaben und zahlen mit umlauten,
   * zahlen, Bindezeichen und whitespaces
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternText = compilePattern("(?U)[" + cc_all + "\\s]+",
      "Keine zeichen ausser: -_");
  /**
   * mindestends 2 belieblig lange grupierungen an klein buchstaben, die mit einem
   * großbuchstaben anfangen, alle von einem wihte space getrennt
   * 
   * @see UserInterface.cc_abg
   * @see UserInterface.cc_abk
   */
  protected Pattern PatternEchtNamen = compilePattern(
      "(?U)[" + cc_abg + "][" + cc_abk + "]*(\\s[" + cc_abg + "][" + cc_abk + "]*)+",
      "Darf keine Zeichen, Zahlen oder extra Leerzeichen enthalten.\nz.B: Hanna Maria Antonia Rodrigez");
  /**
   * beliebig viele und lange grupierungen an allen zeichen die von 1 whitespace
   * getrennt werden
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternArtikel = compilePattern("(?U)[" + cc_all + "]+(\\s[" + cc_all + "]+)*",
      "Darf keine Sonderzeichen oder extra Leerzeichen enthalten.");
  /**
   * 1ne positieve beliebig lange nummer ohne whitespaces
   * 
   * @see UserInterface.cc_n
   */
  protected Pattern PatternNum = compilePattern("(?U)[" + cc_n + "]+",
      "Eine beliebig lange positive Zahl ohne Nachkommastelle.\nz.B. 100, 4, 99999999");
  /**
   * 1 oder kein minus, 1ne beliebig lange nummer
   * 
   * @see UserInterface.cc_n
   */
  protected Pattern PatternInt = compilePattern("(?U)(-)?[" + cc_n + "]+",
      "Eine beliebig lange Zahl ohne Nachkommastelle.\nz.B: 1, 99, -7");
  /**
   * 1 oder kein minus, 1ne beliebig lange nummer, optional ein punkt und 1ne
   * beliebig lange nummer
   * 
   * @see UserInterface.cc_n
   * @see UserInterface.cc_all
   */
  protected Pattern PatternFloat = compilePattern("(?U)(-)?[" + cc_n + "]+((.)[" + cc_all + "]+)?",
      "Eine beliebig lange Zahl.\nz.B: 9, 20, 11.5, -99.003");
  /**
   * eine email adresse
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternEmail = compilePattern("(?U)[" + cc_all + "]+(@)[" + cc_all + "]+(.)[" + cc_all + "]+",
      "Eine Email: 'xxxx@xxxx.xxx'\nz.B: max.mustermann99@test.de");
  /**
   * nach diesem format: StraßenName 11
   * 
   * @see UserInterface.cc_ab
   * @see UserInterface.cc_wB
   * @see UserInterface.cc_ab
   * @see UserInterface.cc_n
   */
  protected Pattern PatternAdresseStraße = compilePattern(
      "(?U)[" + cc_ab + "]+(\\s|" + cc_wB + "[" + cc_ab + "]+)*\\s[" + cc_n + "]+",
      "Ein Straßen Name z.B. Hans-Peter Friedrich Weg 22");
  /**
   * nach diesem format: 99999 Ortsname
   * 
   * @see UserInterface.cc_n
   * @see UserInterface.cc_abg
   * @see UserInterface.cc_abk
   * @see UserInterface.cc_wB
   * @see UserInterface.cc_ab
   */
  protected Pattern PatternAdresseOrt = compilePattern(
      "(?U)[" + cc_n + "]{5}\\s[" + cc_abg + "][" + cc_abk + "]*(\\s|" + cc_wB + "[" + cc_ab + "])*",
      "Ein Orts Name z.B. 22222 Über-Unter Beispielhausen");
  /**
   * nach diesem format: Deutschland
   * 
   * @see UserInterface.cc_abg
   * @see UserInterface.cc_abk
   */
  protected Pattern PatternAdresseLand = compilePattern("(?U)[" + cc_abg + "][" + cc_abk + "]+",
      "Ein Lands Name z.B. Deutschland");
  /**
   * ein passwort
   * 
   * @see UserInterface.cc_all
   * @see UserInterface.cc_sz
   */
  protected Pattern PatternPasswort = compilePattern("(?U)[" + cc_all + cc_sz + "\\s]+",
      "Darf keine sonderzeichen enthalten ausser: " + cc_wB + cc_sz);
  /**
   * 1ne beliebig lange grupierungen an Buchstaben mit umlauten, zahlen, und Binde
   * zeichen ohne whitespaces
   * 
   * @see UserInterface.cc_all
   */
  protected Pattern PatternUserName = compilePattern("(?U)[" + cc_all + "\\S]+",
      "Darf Buchstaben, Nummern und Zeichen enthalten ausser: " + cc_sz + "\nUnd keine Leerzeichen enthalten.");

  // #endregion
  // #region check input

  /**
   * check ob ein String input in das vorgegebene pattern fällt
   * alle vordefinierten patterns sind true wenn der string akzeptierbar zur
   * benutzung ist
   * 
   * @param pattern regex pattern oder null
   * @param string  string zu überprüfen
   * @return boolean
   */
  protected boolean CheckString(Pattern pattern, String string) {
    return pattern.matcher(string).matches();
  }

  /**
   * check ob ein String input in das vorgegebene pattern fällt
   * alle vordefinierten patterns sind true wenn der string akzeptierbar zur
   * benutzung ist
   * 
   * @param pattern        regex pattern oder null
   * @param string         string zu überprüfen
   * @param wrongInputText custom text der erklärt welche inputs erlaubt sind,
   *                       null wenn der pattern spezifische text genommen werden
   *                       soll
   * @throws ExceptionInputFalsch
   */
  protected void CheckString(Pattern pattern, String string, String wrongInputText) throws ExceptionInputFalsch {
    // wenn apttern icht true erstellt exception mit fehlermeldung
    if (!CheckString(pattern, string)) {
      if (wrongInputText == null && patternInfoText.containsKey(pattern))
        throw new ExceptionInputFalsch(getPatternInfo(pattern));

      throw new ExceptionInputFalsch(wrongInputText);
    }
  }

  // #endregion

  // #region debug

  /**
   * ist nur als debug gedacht
   * 
   * @param pat
   */
  protected void regexChecker(Pattern pat) {
    String input;
    BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
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

  // #endregion
}