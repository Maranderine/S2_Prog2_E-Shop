
package Domain.EreignisLog;

import java.util.Vector;

import Domain.Eshop;

import Domain.BenutzerObjekte.Benutzer;


public class EreignisLogVerwaltung {

  private int EreignisZaehler;
  private Eshop meinShop;

  private Vector<Ereignis> log = new Vector<Ereignis>();

  /**
   * EreignisLogVerwaltung
   * 
   * @param eshop Eshop
   */
  public EreignisLogVerwaltung(Eshop eshop) {
    // get verwaltungen von eshop
    this.meinShop = eshop;

    // TODO get ereignis Zähler: load oder 1
    this.EreignisZaehler = 1;
  }

  // #region neues ereignis//////////////////////////////////////////////////////

  public Ereignis createEreignis(byte[] userHash, String type, String[] searchTermsArr) {
    Benutzer user = this.meinShop.BV_getAktiverBenutzer(userHash);
    if (user != null) {
      
      
      // add new event to log
      // Ereignis ereignis = new EreignisArtikelDelete(useZaehler(), "TEST", user, CUserNumber, CUserType, searchTerms,
      //     artikel, artikelNumber, artikelName, artikelBestand, artikelPreis);
      // this.log.add(ereignis);

      // return ereignis;
    }
    // return true;
    return null;
  }

  // #endregion///////////////////////////////////////////////////////////

  /**
   * displays the Ereignis Log
   * 
   * @return ereignis log string
   */
  public String displayLog() {
    return toString();
  }

  /**
   * return and increment Ereigniszaehler
   * 
   * @return
   */
  protected int useZaehler() {
    // returns original value and increases by one
    return this.EreignisZaehler++;
  }

  @Override
  public String toString() {
    String str = "EREIGNIS LOG:\n";
    if (this.log.isEmpty()) {
      // log empty
      str += "\tKeine Ereignisse\n";
    } else {
      // log not empty
      for (Ereignis ereignis : this.log) {
        str += ereignis.toString() + "\n";
      }
    }

    return str;
  }
}
