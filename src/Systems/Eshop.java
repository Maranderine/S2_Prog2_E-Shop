package Systems;

import java.util.HashMap;

import DatenObjekte.Artikel;
import DatenObjekte.Warenkorb;

public class Eshop {

  private static Warenkorb warenkorb;

  enum UserType {
    NONE,
    KUNDE,
    MITARBEITER
  }

  private static UserType userType;

  private Eshop() {
    userType = UserType.NONE;
  }

  // #region LOGIN/LOGOUT
  private static void Login() {

    switch (userType) {
      case KUNDE:
        LoginKundeSetup();
        break;
      case MITARBEITER:
        LoginMitarbeiterSetup();
        break;
      default:

        break;
    }
  }

  private static void Logout() {
    userType = UserType.NONE;
  }

  /**
   * sets up one time prerequisites for Kunde functionality to work
   */
  private static void LoginKundeSetup() {
    warenkorb = new Warenkorb();

  }

  /**
   * sets up one time prerequisites for Mitarbeiter functionality to work
   */
  private static void LoginMitarbeiterSetup() {

  }

  // #endregion
  // #region warenkorb
  private static HashMap<Artikel, Integer> WK_getInhalt() {
    return warenkorb.getInhalt();
  }

  private static void WK_setInhalt(HashMap<Artikel, Integer> inhalt) {
    warenkorb.setInhalt(inhalt);
  }

  private static void WK_setArtikel(Artikel artikel, Integer integar) {
    warenkorb.setArtikel(artikel, integar);
  }

  private static void WK_removeArtikel(Artikel artikel) {
    warenkorb.removeArtikel(artikel);
  }

  private static void WK_clearAll() {
    warenkorb.clearAll();
  }
  // #endregion
}
