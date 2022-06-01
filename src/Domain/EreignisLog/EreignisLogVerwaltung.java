
package Domain.EreignisLog;

import java.util.Vector;

import Domain.Eshop;
import Domain.BenutzerObjekte.Benutzer;

/**
 * EreignisLogVerwaltung
 */
public class EreignisLogVerwaltung {

  protected int EreignisZaehler;
  private Eshop meinShop;

  protected Vector<Ereignis> log = new Vector<Ereignis>();

  public EreignisLogVerwaltung(Eshop eshop) {

    this.meinShop = eshop;

    // TODO get ereignis Zähler: load oder 1
    this.EreignisZaehler = 1;
  }

  // #region neues ereignis//////////////////////////////////////////////////////
  private Ereignis createEreignis(byte[] userHash, String type) {
    Benutzer user = this.meinShop.BV_getAktiverBenutzer(userHash);
    if (user != null) {
      // add new event to log
      Ereignis ereignis = new Ereignis(useZaehler(), user, type);
      this.log.add(ereignis);

      return ereignis;
    }
    // return true;
    return null;
  }

  public boolean neuesEreignis(byte[] userHash, String type) {
    Ereignis ereignis = createEreignis(userHash, type);
    return (ereignis != null);
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
